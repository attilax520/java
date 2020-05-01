package com.kok.sport.utils.db;

public class UniqueEx extends RuntimeException {

	public UniqueEx(String sql) {
		super(sql);
	}

}
