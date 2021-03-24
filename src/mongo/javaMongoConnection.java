package mongo;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import gym_manger_interfaces.DefaultClass;
import javafx.application.Application;
import javafx.stage.Stage;

public class javaMongoConnection extends Application {

    public static  DB db;

    public static void main (String args[]){
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MongoClient mongoClient = new MongoClient("localhost",27017);
        System.out.println("server connection established");

        db = mongoClient.getDB("GymDatabases");
        System.out.println("connect to database succesful");
        System.out.println("Database Name"+db.getName());

        //create collection
        DBCollection collection1=db.getCollection("DefaultMember");
        System.out.println("collection created succefully");
    }
}
