package com.board.dao;

import com.board.VO.BoardVO;
import com.board.dao.BoardDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;

@Component
public class BoardDaoImpl extends SqlSessionDaoSupport implements BoardDao {
	
	public List<BoardVO> list(Map<String, Object> map) {
		List<BoardVO> list = getSqlSession().selectList("boardList", map);
		return list;
	}
	public List<BoardVO> listSearch(Map<String, Object> map) {
		List<BoardVO> list = getSqlSession().selectList("boardListSearch", map);
		return list;
	}

	public int getCount(Map<String, Object> map) {
		return ((Integer) getSqlSession().selectOne("boardCount", map)).intValue();
	}
	
	public int getCountSearch(Map<String, Object> map) {
		return ((Integer) getSqlSession().selectOne("boardCountSearch", map)).intValue();
	}

	public BoardVO boardInfo(int seq) {
		return getSqlSession().selectOne("boardInfo", seq);
	}

	public int boardDelete(int seq) {
		return getSqlSession().delete("boardDelete", seq);
	}
	public int replyDelete(int seq) {
		return getSqlSession().delete("replyDelete", seq);
	}

	public int boardpassC(Map<String, Object> map) {
		return getSqlSession().selectOne("boardPassC", map);
	}

	public int boardHitPlus1(int seq) {
		return getSqlSession().update("boardHitPlus1", seq);
	}

	public int newBoardInsert(BoardVO boardVO) {
		return getSqlSession().insert("newBoardInsert", boardVO);
	}

	public int boardseqCurrval() {
		return getSqlSession().selectOne("boardseqCurrval");
	}

	public int boardFileSave(Map<String, Object> map) {
		return getSqlSession().insert("boardFileSave",map);
	}

	public String boardFileCheck(int seq) {
		return getSqlSession().selectOne("boardFileCheck",seq);
	}

	public int boardFileDelete(int seq) {
		return getSqlSession().delete("boardFileDelete", seq);
	}
	
	public int boardUpdate(BoardVO boardVO) {
		return getSqlSession().update("boardUpdate", boardVO);
	}

	public int boardFileUpdate(Map<String, Object> map) {
		return getSqlSession().update("boardFileUpdate",map);
	}
	
	public int replyCount(int seq) {
		return getSqlSession().selectOne("replyCount", seq);
		
	}
	
	public int replySave(Map<String, Object> map) {
		return getSqlSession().insert("replySave",map);
	}

	public int replyOriginno(int seq) {
		return getSqlSession().selectOne("replyOriginno", seq);
	}

	public int replySeq(int seq) {
		return getSqlSession().selectOne("replySeq",seq);
	}

	public int replyCountMax(int seq) {
		return getSqlSession().selectOne("replyCountMax", seq);
	}
	
	public int originnoUpdate(Map<String, Object> map) {
		return getSqlSession().update("originnoUpdate",map);
	}

}
