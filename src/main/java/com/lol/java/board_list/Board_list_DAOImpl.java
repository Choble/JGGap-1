package com.lol.java.board_list;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("board_list_DAO")
public class Board_list_DAOImpl implements Board_list_DAO{

	@Autowired
	private SqlSessionTemplate mybatis;

	@Override
	public List<Board_list_VO> admin_boardList() {
		return mybatis.selectList("Board_list_DAO.admin_boardList");
	}
	
	@Override
	public List<Board_list_VO> boardList(String result) {
		return mybatis.selectList("Board_list_DAO.boardList", result);
	}

//	@Override
//	public List<Board_list_VO> search_boardList() {
//		return mybatis.selectList("Board_list_DAO.search_boardList");
//	}


	
}
