package edu.escuelaing.arep.database;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;
import com.mongodb.client.MongoCollection;
import edu.escuelaing.arep.entry.Message;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ConnectionMongoDB {
    String uri;
    MongoClient mongoClient;


    public ConnectionMongoDB() throws ParseException {
        //https://programmerclick.com/article/8253664516/
        uri = "mongodb://mongo-db:27017";
        ConnectionString connection = new ConnectionString(uri);
        this.mongoClient = MongoClients.create(connection);
    }
    public void insertMessage(Message message){
        MongoDatabase mongoDatabase = mongoClient.getDatabase("juliamongodb");
        //mongoClient = new MongoClient(uri);
        MongoDatabase db = mongoClient.getDatabase("juliamongodb");
        MongoCollection<Document> collection = db.getCollection("messages");
        Document  document =new Document();
        document.put("content",message.getContent());
        document.put("date",message.getDate());
        collection.insertOne(document);
    }

    public ArrayList<Message> getTenMessages() {
        MongoDatabase database = mongoClient.getDatabase("juliamongdb");
        MongoCollection<Document> collection = database.getCollection("messages");
        FindIterable<Document> findIterable = collection.find();
        ArrayList<Document> documents = new ArrayList<Document>();
        ArrayList<Message> messages = new ArrayList<Message>();
        findIterable.into(documents);
        for (Document doc : documents) {
            if (doc.get("content") != null && doc.get("date") != null) {
                messages.add(new Message((String) doc.get("content"), (String)doc.get("date")));
            }
        }
        ArrayList<Message> messagesToShow = new ArrayList<Message>();
        int last = messages.size()-1;
        for (int count = 10 ;  count > 0 && count <= 10 && 0<= last && last< messages.size() ; count -- ){
                messagesToShow.add(messages.get(last));
                last = last -1;
        }
        for (Message m : messagesToShow)
            System.out.println("----Mensaje"+ m.getDate() + m.getContent());
        return messagesToShow;
    }
}