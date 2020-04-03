package com.yinduowang.installment.mvp.model.entity;

import java.util.List;

public class MyProvince {
	private String name;
	private List<City> city;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<City> getCity() {
		return city;
	}
	public void setCity(List<City> city) {
		this.city = city;
	}
	
}
