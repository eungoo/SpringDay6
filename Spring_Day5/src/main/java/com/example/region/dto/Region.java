package com.example.region.dto;

import java.util.List;

import lombok.Data;

@Data
public class Region {
	private Integer regionId;
	private String regionName;
	
	private List<Country> counties;
}
