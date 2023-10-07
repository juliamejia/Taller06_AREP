package edu.escuelaing.arep;

import static spark.Spark.*;

public class App {
    public static void main( String[] args ) {
        port(getPort());

        staticFileLocation("/static");

        LBRoundRobin LBRR = new LBRoundRobin();

        get("/", (req, res) -> {
            res.redirect( "index.html");
            return null;
        });

        get("/results", (req, res) -> {
            System.out.println("Entre en result en get");
            res.status(200);
            res.type("application/json");
            String resp = LBRR.getMessages();
            LBRR.changeServer();
            return resp;
        });

        post("/insert", (req, res) -> {
            System.out.println("Entre en result en post");
            LBRR.postMessage(req.body());
            LBRR.changeServer();
            return "";
        });
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}