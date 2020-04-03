package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class MyRelationBean implements Serializable {


	private List<RelationBean> lineal_list;//直系亲属关系
	private List<RelationBean> other_list;//其他联系人关系
	/**
	 * lineal_relation : 1
	 * lineal_name : 我和哥哥
	 * lineal_mobile : 13961054289
	 * other_relation : 8
	 * other_name : 小雪1
	 * other_mobile : 15216615443
	 */

	private String lineal_relation;//与用户的关系ID :1、父亲 3、母亲  9、儿子  10、女儿  11、兄弟  12、姐妹  2、配偶
	private String lineal_name;// 直系联系人名称
	private String lineal_mobile;// 直系联系人手机号
	private String other_relation;//与用户的关系ID :6、朋友   5、同学  7、同事 8、亲戚  100、其他
	private String other_name;// 其他联系人姓名
	private String other_mobile;// 其他手机号

	private int special;
	private String url;



	public List<RelationBean> getOther_list()
	{
		return other_list;
	}

	public void setOther_list(List<RelationBean> other_list)
	{
		this.other_list = other_list;
	}

	public List<RelationBean> getLineal_list()
	{
		return lineal_list;
	}

	public void setLineal_list(List<RelationBean> lineal_list)
	{
		this.lineal_list = lineal_list;
	}

	public String getLineal_relation() {
		return lineal_relation;
	}

	public void setLineal_relation(String lineal_relation) {
		this.lineal_relation = lineal_relation;
	}

	public String getLineal_name()
	{
		return lineal_name;
	}

	public void setLineal_name(String lineal_name)
	{
		this.lineal_name = lineal_name;
	}

	public String getLineal_mobile()
	{
		return lineal_mobile;
	}

	public void setLineal_mobile(String lineal_mobile)
	{
		this.lineal_mobile = lineal_mobile;
	}

	public String getOther_relation() {
		return other_relation;
	}

	public void setOther_relation(String other_relation) {
		this.other_relation = other_relation;
	}

	public String getOther_name()
	{
		return other_name;
	}

	public void setOther_name(String other_name)
	{
		this.other_name = other_name;
	}

	public String getOther_mobile()
	{
		return other_mobile;
	}

	public void setOther_mobile(String other_mobile)
	{
		this.other_mobile = other_mobile;
	}
	public int getSpecial() {
		return special;
	}

	public void setSpecial(int special) {
		this.special = special;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}