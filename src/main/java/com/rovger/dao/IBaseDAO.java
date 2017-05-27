package com.rovger.dao;

/**
 * Created by weijlu on 2017/5/24.
 */
public interface IBaseDAO<T> {

	void add(T entity, String collection);
	void del(String str, String collection);
	T update(T entity, String collection);
	T searchByName(String str, String collection);
}
