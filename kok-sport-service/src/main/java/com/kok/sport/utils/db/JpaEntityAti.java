package com.kok.sport.utils.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JpaEntityAti")
public class JpaEntityAti {
	   public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Id @GeneratedValue
       private long id;//主键.
	 private String name;//测试名称.
}
