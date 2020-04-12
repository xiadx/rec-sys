package mongodb;

import com.mongodb.MongoClientURI;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBDemoIII {

    public static void main(String[] args) {
        try {
//            MongoClientURI uri = new MongoClientURI("mongodb://readany:Mfw09uygt@10.133.1.36:28116,192.168.4.250:28116,192.168.2.230:28116/mspider_price?authSource=admin&readPreference=secondaryPreferred&replicasSet=eccrawler&maxIdleTimeMS=30000");
            MongoClientURI uri = new MongoClientURI("mongodb://mspider_price:Mfw12Uyt02Gw@10.133.1.36:28116,192.168.4.250:28116,192.168.2.230:28116/mspider_price?authSource=mspider_price&readPreference=secondaryPreferred&replicasSet=eccrawler&maxIdleTimeMS=30000");
            MongoClient mongoClient = new MongoClient(uri);
            MongoDatabase mongoDatabase = mongoClient.getDatabase("mspider_price");
            System.out.println("Connect to database successfully");
            MongoCollection<Document> collection = mongoDatabase.getCollection("video_info");
            System.out.println("Collection switch video_info successfully");
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            while (mongoCursor.hasNext()) {
                System.out.println(mongoCursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
