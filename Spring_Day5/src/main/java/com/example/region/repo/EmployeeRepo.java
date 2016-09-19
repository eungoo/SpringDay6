package com.example.region.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepo {
	@Autowired
	JdbcTemplate jt;
	
	public String selectEmplByEmail(String email){
	
		String sql = "select first_name from employees where email=?";
		return jt.queryForObject(sql,String.class,email);
	}
	
	
	
	
	
}
