package com.yinduowang.installment.mvp.model.entity;


import java.io.Serializable;

public class GetRelationResponseBean implements Serializable {

	private MyRelationBean item;
	
	public MyRelationBean getItem() {
		return item;
	}

	public void setItem(MyRelationBean item) {
		this.item = item;
	}
}
