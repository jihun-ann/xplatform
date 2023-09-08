package com.board.controller;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.VO.BoardVO;
import com.board.dao.BoardDao;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WriteController {
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private BoardService boardService;
	@Value("C:/RootSystem/spring/WebContent/board/file")
	private String path;
	
	
	/*#####페이지처리#####*/
	
	@RequestMapping({ "/board/write.do" })
	public ModelAndView process(){
	System.out.println("글쓰기 레이아웃 전 컨트롤러");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("write");

		return mav;
	}
	
	
	
	@RequestMapping("/board/boardRetouchP.do")
	public ModelAndView boardRetouchPage(HttpServletRequest req){
		
		ModelAndView mav = new ModelAndView();
		String seq = req.getParameter("seq");
		if(!seq.equals("")||!seq.equals(null)){
			int seqint = Integer.parseInt(seq);
			BoardVO vo = this.boardDao.boardInfo(seqint);
			
			mav.setViewName("retouch");
			mav.addObject("board",vo);
			
			String resstring = this.boardDao.boardFileCheck(seqint);
			if(resstring != null){
				mav.addObject("filename", resstring);
			}
			
			return mav;
		}
		
		mav.setViewName("redirect:list.do");
		return mav;
	}
	
	

	@RequestMapping("/board/boardReply.do")
	public ModelAndView boardReplyPage(HttpServletRequest req){
		ModelAndView mav = new ModelAndView();
		int seq = Integer.parseInt(req.getParameter("seq"));
		
		BoardVO vo = this.boardDao.boardInfo(seq);
		if(vo == null){
			mav.setViewName("redirect:list.do");
			return mav;
		}
		
		int replyCount = this.boardDao.replyCount(vo.getSeq());
		int originno = this.boardDao.replyOriginno(vo.getSeq());
		int replyseq = this.boardDao.replySeq(vo.getSeq());

		if(replyCount == 0){
			replyCount = this.boardDao.replyCountMax(originno);
			replyCount++;
		}
		
		mav.setViewName("reply");
		mav.addObject("originno",vo.getSeq());
		mav.addObject("replyno",replyCount);
		mav.addObject("replyseq",replyseq+1);
		return mav;
	} 
	
	
	
	
	
	
	
	

	/*#####데이터 처리#####*/
	@RequestMapping("/board/insertBoard.do")
	public ModelAndView insertBoard(
			@ModelAttribute BoardVO board, @RequestParam(value="boardfile",required=false)MultipartFile boardfile){
		ModelAndView mav = new ModelAndView();
		int res = this.boardDao.newBoardInsert(board);
		
		if(res == 0 || boardfile.getSize()>50000000){
			mav.setViewName("redirect:error.do");
			return mav;
		}
		int resnum = this.boardDao.boardseqCurrval();
		board = this.boardDao.boardInfo(resnum);
		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("originno", "null");
		map.put("replyno", 0);
		map.put("replyseq", 0);
		map.put("seq", board.getSeq());
		
		int res2 = this.boardDao.replySave(map);
		
		if(res2 == 0){
			mav.setViewName("redirect:error.do");
			return mav;
		}
		
		if(!boardfile.isEmpty()){
			int result = boardService.boardFileSave(boardfile, board.getSeq());
			if(result != 1){
				mav.setViewName("redirect:error.do");
				return mav;
			}
		}
		
		mav.setViewName("redirect:list.do");
		return mav;
	}
	
	
	@RequestMapping("/board/insertReply.do")
	public ModelAndView insertReply(
			@ModelAttribute BoardVO board, @RequestParam(value="boardfile",required=false)MultipartFile boardfile,
			@RequestParam("originno") String originno,@RequestParam("replyno") String replyno,@RequestParam("replylv") String replylv){
		ModelAndView mav = new ModelAndView();
		
		
		int res = this.boardDao.newBoardInsert(board);
		
		if(res == 0 || boardfile.getSize()>50000000){
			mav.setViewName("redirect:error.do");
			return mav;
		}
		int resnum = this.boardDao.boardseqCurrval();
		board = this.boardDao.boardInfo(resnum);
		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("originno", originno);
		map.put("replyno", replyno);
		map.put("replyseq", replylv);
		map.put("seq", board.getSeq());
		
		int res2 = this.boardDao.replySave(map);
		
		if(res2 == 0){
			mav.setViewName("redirect:error.do");
			return mav;
		}
		if(!boardfile.isEmpty()){
			int result = boardService.boardFileSave(boardfile, board.getSeq());
			if(result != 1){
				mav.setViewName("redirect:error.do");
				return mav;
			}
		}
		mav.setViewName("redirect:list.do");
		return mav;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/board/boardCDelete.do",  produces = "application/json", method=RequestMethod.POST)
	public  void deleteBoardC(HttpServletRequest request, HttpServletResponse respone) throws IOException{
		
		int seqint = Integer.parseInt(request.getParameter("seq"));
		String pass = request.getParameter("pass");
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("seq", seqint);
		map.put("pass", pass);
		
		int vo = this.boardDao.boardpassC(map);
		int result = 0;
		
		String resstring = this.boardDao.boardFileCheck(seqint);
		if(resstring!=null){
			this.boardDao.boardFileDelete(seqint);
			String ext = FilenameUtils.getExtension(resstring);
			int fileR = boardService.boardFileDelete(seqint,ext);
		}
		
		if(vo == 1){
			int originno = this.boardDao.replyOriginno(seqint);
			HashMap<String, Object> map2 = new HashMap<>();
			map2.put("seq", seqint);
			if(originno == 0){
				map2.put("originno", 0);
			}else{
				map2.put("originno", originno);
			}
			this.boardDao.originnoUpdate(map2);
			
			this.boardDao.replyDelete(seqint);
			this.boardDao.boardDelete(seqint);
			result = 1;
		}
		
		PrintWriter writer = respone.getWriter();
		writer.println(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/board/boardCUpdate.do",  produces = "application/json", method=RequestMethod.POST)
	public  void updateBoard(HttpServletRequest request, HttpServletResponse respone) throws IOException{
		
		int seqint = Integer.parseInt(request.getParameter("seq"));
		String pass = request.getParameter("pass");
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("seq", seqint);
		map.put("pass", pass);
		
		int vo = this.boardDao.boardpassC(map);
		int result = 0;
		
		if(vo == 1){
			result = 1;
		}
		
		PrintWriter writer = respone.getWriter();
		writer.println(result);
	}
	
	

	
	@RequestMapping("/board/boardRetouch.do")
	public ModelAndView updateBoard(
			@ModelAttribute BoardVO board, @RequestParam(value="boardfile",required=false)MultipartFile boardfile,
			@RequestParam("fileDConfirm") String dConfirm){
		ModelAndView mav = new ModelAndView();
		
		int res = this.boardDao.boardUpdate(board);
		
		if(res == 0 || boardfile.getSize()>50000000){
			mav.setViewName("redirect:error.do");
			return mav;
		}
		
		String deleteFile = this.boardDao.boardFileCheck(board.getSeq());
		
		if(dConfirm.equals("y")){
			String ext = FilenameUtils.getExtension(deleteFile);
			int fileR = boardService.boardFileDelete(board.getSeq(),ext);
			this.boardDao.boardFileDelete(board.getSeq());
		}
		
		
		if(!boardfile.isEmpty()){
			if(deleteFile == null){
				int result = boardService.boardFileSave(boardfile, board.getSeq());
				if(result != 1){
					mav.setViewName("redirect:error.do");
					return mav;
				}
			}else{
				int result = boardService.boardFileUpdate(boardfile, board.getSeq());
				if(result != 1){
					mav.setViewName("redirect:error.do");
					return mav;
				}
			}
		}
		
		mav.setViewName("redirect:info.do?seq="+board.getSeq());
		return mav;
	}
	
	
}

