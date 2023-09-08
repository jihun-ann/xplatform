package com.board.controller;

import org.springframework.web.multipart.MultipartFile;

public interface BoardService {

	public abstract int boardFileSave(MultipartFile file,int seq);
	public abstract int boardFileUpdate(MultipartFile file,int seq);
	public abstract int boardFileDelete(int seq, String ext);
}
