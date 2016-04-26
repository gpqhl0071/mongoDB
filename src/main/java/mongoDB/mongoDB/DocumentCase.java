package mongoDB.mongoDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * MongoDB api 参考:http://api.mongodb.org/java/3.2/ 对文档的增删改查操作
 */
public class DocumentCase {

	public static String ip = "192.168.1.95";
	public static int port = 27017;

	public static void main(String args[]) {
		// insert();
		queryAll();
		// queryByName("小黄");
		// update();
		// del();
	}

	/**
	 * 查询所有数据 @author: gao peng @param: @return: void @throws
	 */
	public static void queryAll() {
		try {
			
	        // 创建MongoDB服务器地址对象
	        ServerAddress address = new ServerAddress(ip, port);
	        // 创建MongoDB服务器用户验证对象
	        MongoCredential credential = MongoCredential.createCredential("admin",
	                "admin",
	                "admin".toCharArray());
			
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient(address, Arrays.asList(credential));

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("school");
			MongoCollection<Document> collection = mongoDatabase.getCollection("student");
			
			/*// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient(ip, port);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("school");
			MongoCollection<Document> collection = mongoDatabase.getCollection("student");*/

			// 检索所有文档
			/**
			 * 1. 获取迭代器FindIterable<Document> 2. 获取游标MongoCursor<Document> 3.
			 * 通过游标遍历检索出的文档集合
			 */

			FindIterable<Document> findIterable = collection.find();
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/**
	 * 根据用户名查询 @author: gao peng @param: @return: void @throws
	 */
	public static void queryByName(String name) {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient(ip, port);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("school");
			MongoCollection<Document> collection = mongoDatabase.getCollection("student");

			BasicDBObject condition = new BasicDBObject();
			condition.put("name", name);

			FindIterable<Document> findIterable = collection.find(condition);
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/**
	 * 生成数据 @author: gao peng @param: @return: void @throws
	 */
	public static void insert() {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient(ip, port);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("school");
			MongoCollection<Document> collection = mongoDatabase.getCollection("student");

			List<Document> documents = new ArrayList<Document>();
			for (int i = 0; i < 100; i++) {
				Document document = new Document("name", "李" + i).append("age", (int) (Math.random() * 50))
						.append("address", "黑龙江").append("scope", (int) (Math.random() * 100))
						.append("inputTime", DateUtils.addDays(new Date(), i));
				documents.add(document);
			}

			collection.insertMany(documents);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/**
	 * 更新数据 @author: gao peng @param: @return: void @throws
	 */
	public static void update() {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient(ip, port);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("school");
			MongoCollection<Document> collection = mongoDatabase.getCollection("student");

			// 更新文档 将文档中likes=100的文档修改为likes=200
			collection.updateMany(Filters.eq("name", "李1"), new Document("$set", new Document("scope", 90)));

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/**
	 * 
	 * @author: gao peng @param: @return: void @throws
	 */
	public static void del() {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient(ip, port);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("school");
			MongoCollection<Document> collection = mongoDatabase.getCollection("student");

			// 删除符合条件的第一个文档
			collection.deleteOne(Filters.eq("name", "李2"));

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
