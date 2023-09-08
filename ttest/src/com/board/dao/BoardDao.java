package com.board.dao;

import com.board.VO.BoardVO;

import java.util.List;
import java.util.Map;

public abstract interface BoardDao {
	
	public abstract List<BoardVO> list(Map<String, Object> paramMap);
	public abstract List<BoardVO> listSearch(Map<String, Object> paramMap);

	public abstract int getCount(Map<String, Object> paramMap);
	public abstract int getCountSearch(Map<String, Object> paramMap);

	public abstract BoardVO boardInfo(int seq);
	
	public abstract int boardHitPlus1(int seq);
	
	public abstract int boardpassC(Map<String, Object> paramMap);

	public abstract int boardDelete(int seq);
	
	public abstract int replyDelete(int seq);
	
	public abstract int boardFileDelete(int seq);
	
	public abstract int newBoardInsert(BoardVO boardVO);
	
	public abstract int boardseqCurrval();
	
	public abstract int boardFileSave(Map<String, Object> map);
	
	public abstract String boardFileCheck(int seq);
	
	public abstract int boardUpdate(BoardVO boardVO);
	
	public abstract int boardFileUpdate(Map<String, Object> map);
	
	public abstract int replyCount(int seq);
	
	public abstract int replyOriginno(int seq);
	
	public abstract int replySeq(int seq);
	
	public abstract int replyCountMax(int seq);
	
	public abstract int replySave(Map<String, Object> map);
	
	public abstract int originnoUpdate(Map<String, Object> map);

}
