package edu.escuelaing.arep;

import edu.escuelaing.arep.database.ConnectionMongoDB;

import static spark.Spark.*;
import com.google.gson.Gson;
import edu.escuelaing.arep.entry.Message;

import java.text.ParseException;

public class App 
{
    public static void main( String[] args ) throws ParseException {
        port(getPort());
        ConnectionMongoDB connection = new ConnectionMongoDB();
        get("/messages", (req, res) -> {
            res.status(200);
            res.type("application/json");
            System.out.println( "Estoy obteniendo los mensajes" );
            return new Gson().toJson(connection.getTenMessages());
        });
        post("/messages", (req, res) -> {
            Message newMessage = new Message(req.body());
            connection.insertMessage(newMessage);
            return null;
        });
    }

    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4561;
    }
}
