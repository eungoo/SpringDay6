package com.example.region.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;



@Configuration
@PropertySource("classpath:/config/dbconfig.properties")
@ComponentScan(basePackages={"com.example.region.repo"})
public class ApplicationConfig {
	static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);		
	
	@Bean
	public DataSource dataSource(@Value("${db.driverClassName}")String driver,
								@Value("${db.url}")String url,
								@Value("${db.username}")String id, 
								@Value("${db.password}")String pass){
		
		logger.trace("driver:{}",driver);
		
		BasicDataSource ds = new BasicDataSource();

		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(id);
		ds.setPassword(pass);
		
		return ds;
	}
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource ds){
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		 
		bean.setDataSource(ds);
		String loc = "mybatis/mybatis-config.xml";
		bean.setConfigLocation(new ClassPathResource(loc));
		
		return bean;
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactoryBean bean) throws Exception{
		SqlSessionTemplate template = new SqlSessionTemplate(bean.getObject());
		return template;		
	}	
	
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource ds){
		return new JdbcTemplate(ds);
	}
	
	
}
