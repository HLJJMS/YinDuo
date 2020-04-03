package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;

public class RelationBean implements Serializable {

	private String id;//类型
	private String name;//名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
