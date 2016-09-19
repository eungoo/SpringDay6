package com.example.region.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.region.dto.Country;
import com.example.region.dto.Region;

@Repository
public class RegionRepo {

	@Autowired
	JdbcTemplate jt;

	// 현재 region에 있는 데이터의 갯수?
	public Integer getRegionCnt() {
		String sql = "select count(*) from regions";
		Integer result = jt.queryForObject(sql, Integer.class);
		return result;
	}

	public List<String> getRegionName() {
		String sql = "select region_name from regions ";
		List<String> result = jt.queryForList(sql, String.class);
		return result;
	}

	public List<Map<String, Object>> getRegion() {
		String sql = "select region_id,region_name from regions ";
		return jt.queryForList(sql);
	}

	public Region getRegion(int regionId) {
		String sql = "select region_id,region_name from regions where region_id=? ";
		return jt.queryForObject(sql, new RegionMapper(), regionId);
	}

	public List<Region> getAllRegion() {
		String sql = "select region_id,region_name from regions";
		return jt.query(sql, new RegionMapper());

	}

	
	public Country getCountryDetail(String countryId) {
		String sql = "select country_id, country_name, region_id, region_name " + "from countries join regions using (region_id) where country_id=?";
		return jt.query(sql, new ResultSetExtractor<Country>() {

			@Override
			public Country extractData(ResultSet rs) throws SQLException, DataAccessException {
				Country country = null;
				CountryMapper cm = new CountryMapper();
				RegionMapper rm = new RegionMapper();
				if (rs.next()) {
					country = cm.mapRow(rs, 0);
					country.setRegion(rm.mapRow(rs, 0));
				}
				return country;
			}
		}, countryId);
	}

	class RegionMapper implements RowMapper<Region> {

		@Override
		public Region mapRow(ResultSet re, int rownum) throws SQLException {
			Region region = new Region();
			region.setRegionId(re.getInt("region_id"));
			region.setRegionName(re.getString("region_name"));
			return region;
		}

	}

	public Region getRegionDetail(int regionId) {
		String sql = "select region_id,region_name,country_id,country_name from regions r join countries c using(region_id) where region_id=?";

		return jt.query(sql, new ResultSetExtractor<Region>() {

			@Override
			public Region extractData(ResultSet rs) throws SQLException, DataAccessException {
				Region region = null;
				if (rs.next()) {
					region = new RegionMapper().mapRow(rs, 0);
					List<Country> counties = new ArrayList<>();
					region.setCounties(counties);
					do {
						Country country = new CountryMapper().mapRow(rs, 0);
						counties.add(country);
					} while (rs.next());
				}

				return region;
			}

		}, regionId);

	}

	class CountryMapper implements RowMapper<Country> {

		@Override
		public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
			Country country = new Country();
			country.setCountryId(rs.getString("country_id"));
			country.setCountyName(rs.getString("country_name"));
			country.setRegionId(rs.getInt("region_id"));

			return country;
		}

	}

}
