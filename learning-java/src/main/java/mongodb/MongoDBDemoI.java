package mongodb;

import java.util.ArrayList;
import java.util.List;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class MongoDBDemoI {

    public static void main(String[] args) {
        try {
            ServerAddress serverAddress = new ServerAddress("172.16.129.177", 28011);
            List<ServerAddress> addrs = new ArrayList<>();
            addrs.add(serverAddress);
            MongoCredential credential = MongoCredential.createScramSha1Credential("kingmind_w", "kingmind", "Mfw12Uyt02Gw".toCharArray());
            List<MongoCredential> credentials = new ArrayList<>();
            credentials.add(credential);
            MongoClient mongoClient = new MongoClient(addrs, credentials);
            MongoDatabase mongoDatabase = mongoClient.getDatabase("kingmind");
            System.out.println("Connect to database successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
