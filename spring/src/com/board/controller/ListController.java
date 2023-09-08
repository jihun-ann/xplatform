package com.board.controller;

import com.board.VO.BoardVO;
import com.board.dao.BoardDao;
import com.board.paging.Paging;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ListController {
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(getClass());
	private int pageSize = 10;
	private int blockCount = 10;
		
	@Autowired
	private BoardDao boardDao;

	@RequestMapping({ "/board/list.do" })
	public ModelAndView process(
			@RequestParam(value = "pageNum", defaultValue = "1") int currentPage,
			@RequestParam(value = "keyField", defaultValue = "") String keyField,
			@RequestParam(value = "keyWord", defaultValue = "") String keyWord) {
		
		String pagingHtml = "";
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HashMap<String, Object> map = new HashMap();
		map.put("keyField", keyField);
		map.put("keyWord", keyWord);
		
		int count = 0;
		
		if(keyField.equals("")||keyWord.equals("")){
			count = this.boardDao.getCount(map);
		}else{
			count = this.boardDao.getCountSearch(map);
		}

		Paging page = new Paging(keyField, keyWord, currentPage, count,
				this.pageSize, this.blockCount, "list.do");

		pagingHtml = page.getPagingHtml().toString();

		map.put("start", Integer.valueOf(page.getStartCount()));
		map.put("end", Integer.valueOf(page.getEndCount()));

		List<BoardVO> list = null;
		if (count > 0) {
			if(keyField.equals("")||keyWord.equals("")){
				list = this.boardDao.list(map);
			}else{
				list = this.boardDao.listSearch(map);
			}
			
		} else {
			list = Collections.emptyList();
		}
		int number = count - (currentPage - 1) * this.pageSize;
		for (int i=0; i<list.size(); i++) {
			if(Integer.parseInt(list.get(i).getReplyseq()) > 0){
				String replytitle="";
				for (int j=0; j<Integer.parseInt(list.get(i).getReplyseq()); j++) {
					replytitle += "ㄴ답글";
				}
				list.get(i).setTitle(replytitle+" : "+list.get(i).getTitle());	
			}
		}
		

		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardList");
		mav.addObject("count", Integer.valueOf(count));
		mav.addObject("currentPage", Integer.valueOf(currentPage));
		mav.addObject("list", list);
		mav.addObject("pagingHtml", pagingHtml);
		mav.addObject("number", Integer.valueOf(number));

		return mav;
	}
	
	@RequestMapping("/board/info.do")
	public ModelAndView infomation(int seq, HttpServletRequest req){
		ModelAndView mav = new ModelAndView();
		BoardVO vo = this.boardDao.boardInfo(seq);
		
		
		if(vo == null){
			mav.setViewName("redirect:list.do");
			return mav;
		}
		else{
			vo.setHit(vo.getHit()+1);
			this.boardDao.boardHitPlus1(seq);
			
			String replyTitle = "";
			if(!vo.getReplyseq().equals("0")){
				for (int i=0; i<Integer.parseInt(vo.getReplyseq()); i++) {
					replyTitle += "ㄴ답글";
				}
				vo.setTitle(replyTitle+" : "+vo.getTitle());
			}
			
			mav.setViewName("boardInfo");
			mav.addObject("boardInfo",vo);
			
			String resstring = this.boardDao.boardFileCheck(seq);
			if(resstring != null){
				String filepath = "file/"+vo.getSeq()+"."+FilenameUtils.getExtension(resstring);
				mav.addObject("filepath",filepath);
				mav.addObject("filename", resstring);
			}
			return mav;
		}
	}
	
	
	
}
