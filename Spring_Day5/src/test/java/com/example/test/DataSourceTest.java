package com.example.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.region.config.ApplicationConfig;
import com.example.region.dto.Country;
import com.example.region.dto.Region;
import com.example.region.repo.EmployeeRepo;
import com.example.region.repo.RegionRepo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ApplicationConfig.class)
public class DataSourceTest {
	static Logger logger = LoggerFactory.getLogger(DataSourceTest.class);

	@Autowired
	DataSource ds;
	@Autowired
	JdbcTemplate jt;
	@Autowired
	RegionRepo regionRepo;	
	@Autowired
	EmployeeRepo empl;
	
	@Before
	public void setUp() throws Exception {
		
	}
	@Test
	public void test() throws SQLException {
		logger.trace("connection :{}",ds.getConnection());
		assertThat(ds,is(notNullValue()));
	}
	
	@Test
	public void jdbctest() throws SQLException {

		assertThat(jt,is(notNullValue()));
	}
	@Test
	public void querytest() throws SQLException {
		Integer cnt = regionRepo.getRegionCnt();
		logger.trace("cnt:{}",cnt);
		assertThat(cnt,is(notNullValue()));
	}
	
	@Test
	public void querytest2() throws SQLException {
		String str = empl.selectEmplByEmail("SKING");
		logger.trace("first name :{}",str);
		assertThat(str,is(notNullValue()));
	}
	@Test
	public void querytest3() throws SQLException {
		List<String> li = regionRepo.getRegionName();
		for(int i = 0 ; i < li.size() ;i++){
			logger.trace("getRegionName:{}",li.get(i));
		}
		
		
		
		assertThat(li,is(notNullValue()));
	}
	@Test
	public void querytest4() throws SQLException {
		List<Map<String,Object>> limap = regionRepo.getRegion();		
		for(int i = 0 ; i < limap.size() ;i++){
			logger.trace("getRegion:{}",limap.get(i));
		}
		assertThat(limap,is(notNullValue()));
	}
	
	@Test
	public void querytest5() throws SQLException {
		Region re = regionRepo.getRegion(99);		
		logger.trace("getRegion:{}",re);
		assertThat(re,is(notNullValue()));
	}
	@Test
	public void querytest6() throws SQLException {
		List<Region> re = regionRepo.getAllRegion();		
		logger.trace("getAllRegion:{}",re);
		for(int i = 0 ; i < re.size() ;i++){
			logger.trace("getRegion:{}",re.get(i));
		}
		assertThat(re,is(notNullValue()));
	}
	
	
	@Test
	public void querytest7() throws SQLException {
		Region region = regionRepo.getRegionDetail(1);
		logger.trace("region: {}",region);
		assertThat(region,is(notNullValue()));
	}
	
	
	@Test
	public void querytest8() throws SQLException {
		Country counties = regionRepo.getCountryDetail("US");
		logger.trace("counties: {}",counties);
		assertThat(counties,is(notNullValue()));
	}
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	@Test
	public void sqltest1() throws SQLException {
		logger.trace("session:{}",sqlSession);
		assertThat(sqlSession,is(notNullValue()));
	}
	

}
