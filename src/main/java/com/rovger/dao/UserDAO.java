package com.rovger.dao;

import com.rovger.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * Mongo Test v1.0
 * Created by weijlu on 2017/5/24.
 */
@Repository
public class UserDAO {

	Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	@Autowired
	private MongoTemplate mongoTemplate;

	public void add(User entity, String collectionName) {
		logger.info("UserDAO.add(),一个用户即将被添加...");
		this.mongoTemplate.insert(entity, collectionName);
		logger.info("UserDAO.add(),一个用户已经被成功添加。");
	}

	public void del(String str, String collectionName) {
		Query queryStr = new Query(Criteria.where("nickname").is(str));
		try {
			logger.info("UserDAO.del(),一个用户即将被删除...");
			this.mongoTemplate.remove(queryStr, User.class);
			logger.info("UserDAO.del(),一个用户已经被删除...");
		} catch (Exception ex) {
			logger.error("UserDAO.del(),出现异常，", ex.getMessage());
		}
	}

	public User update(User entity, String collectionName) {
		logger.info("UserDAO.update(),一个用户即将被更新...");
		Query queryStr = new Query(Criteria.where("nickname").is(entity.getNickname()));
		Update updateStr = new Update()
				.set("password", entity.getPassword())
				.set("email", entity.getEmail())
				.set("telephone", entity.getTelephone())
				.set("creationDate", entity.getCreationDate());
		User user = null;
		try {
			logger.info("更新条件是：nickname="+ entity.getNickname() +",如果没有符合查询数据，则实现insert操作。");
			user = this.mongoTemplate.findAndModify(queryStr, updateStr, new FindAndModifyOptions().returnNew(true).upsert(true), User.class, collectionName);
			logger.info("UserDAO.update(),一个用户已经被更新。");
		} catch (Exception ex) {
			logger.error("UserDAO.update(),出现异常，", ex.getMessage());
		}
		return user;
	}

	public User searchByName(String name, String collectionName) {
		Query query = new Query();
		logger.info("UserDAO.searchByName(),根据name:"+ name + "查询用户信息...");
		query.addCriteria(new Criteria("nickname").is(name));
		User user = null;
		try {
			user = this.mongoTemplate.findOne(query, User.class, collectionName);
			logger.info("UserDAO.searchByName(),根据name:"+ name + "成功查询User信息.");
		} catch (Exception ex) {
			logger.error("searchByName failed.", ex.getMessage());
		}
		return user;
	}
}
