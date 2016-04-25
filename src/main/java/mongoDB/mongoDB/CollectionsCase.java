/*
*Copyright (c) 2016, gp.inc and/or its affiliates. All rights reserved.
*/
package mongoDB.mongoDB;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 操作集合
 * 
 * @author: gao peng
 * @date: 2016年4月25日 下午4:54:26
 *
 */
public class CollectionsCase {
	public static String ip = "192.168.1.95";
	public static int port = 27017;

	public static void main(String[] args) {
		dropDocument();
	}

	/**
	 * 删除collection @author: gao peng @param: @return: void @throws
	 */
	public static void dropDocument() {

		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient(ip, port);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("school");
			MongoCollection<Document> collection = mongoDatabase.getCollection("student");

			collection.drop();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
