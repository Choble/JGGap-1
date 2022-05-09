package com.lol.java.admin;

import java.util.List;

public interface AdminService {

	public List<AdminVO> user_list();
	
	public int member_count();
	
	public void grant(AdminVO vo);

	public int conversion(String user_id);
	
	public int report_count();
	
	public List<AdminVO> report_list();
}
