package com.board.controller;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.board.dao.BoardDao;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private HttpServletRequest req;
	@Autowired
	private BoardDao boardDao;
	
	@Value("C:/RootSystem/spring/WebContent/board/file")
	private String path;
	
	@Override
	public int boardFileSave(MultipartFile file, int seq) {
		File savePath = new File(path);
		//savePath.mkdir();
		
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		
		File saveFile = new File(savePath,seq+"."+ext);
		try {
			file.transferTo(saveFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("seq", seq);
		map.put("filename", file.getOriginalFilename());
		
		this.boardDao.boardFileSave(map);
		
		return 1;
	}

	@Override
	public int boardFileUpdate(MultipartFile file, int seq) {
		String deleteFile = this.boardDao.boardFileCheck(seq);
		
		String ext = FilenameUtils.getExtension(deleteFile);
		boardFileDelete(seq,ext);
		
		File savePath = new File(path);
		ext = FilenameUtils.getExtension(file.getOriginalFilename());
		File saveFile = new File(savePath,seq+"."+ext);
		try {
			file.transferTo(saveFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("seq", seq);
		map.put("filename", file.getOriginalFilename());
		
		this.boardDao.boardFileUpdate(map);
		
		return 1;
	}

	@Override
	public int boardFileDelete(int seq, String ext) {
		//String filename = this.boardDao.boardFileCheck(seq);
				File savePath = new File(path);
				File[] list = savePath.listFiles();
				String res = seq+"."+ext;
				
				if(list.length>0){
					for (int i=0; i<list.length; i++) {
						if(list[i].getName().equals(res)){
							list[i].delete();
						}
					}
				}
				
				return 1;
	}
	
}
