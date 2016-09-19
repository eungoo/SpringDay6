package com.example.region.dto;

import java.util.List;

import lombok.Data;

@Data
public class Country {
	private String countryId;
	private String countyName;
	private Integer regionId;
	
	private Region region;
}
