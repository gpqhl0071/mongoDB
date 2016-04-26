/*
*Copyright (c) 2016, gp.inc and/or its affiliates. All rights reserved.
*/
package mongoDB.mongoDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DocumentAggregateCase {

	public static String ip = "192.168.1.95";
	public static int port = 27017;

	public static void main(String args[]) {
		queryAll();
	}

	/**
	 * 	通过aggregate操作mongoDB
	 * 
	 *  @author: gao peng 
	 *  @param: 
	 *  @return: void 
	 *  @throws
	 */
	public static void queryAll() {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient(ip, port);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("school");
			MongoCollection<Document> collection = mongoDatabase.getCollection("student");

			List<Bson> bsonList = new ArrayList<Bson>();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sum", new BasicDBObject("$sum", 1));
			map.put("_id", "$scope");
			BasicDBObject groupValue = new BasicDBObject(map);

			BasicDBObject groupBson = new BasicDBObject();
			groupBson.put("$group", groupValue);
			
			BasicDBObject sortBson = new BasicDBObject();
			sortBson.put("$sort", new BasicDBObject("sum", -1));

			bsonList.add(groupBson);
			bsonList.add(sortBson);

			AggregateIterable<Document> iter = collection.aggregate(bsonList);
			for (Document d : iter) {
				System.out.println("_id = " + d.get("_id") + ",sum = " + d.get("sum"));
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
