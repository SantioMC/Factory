package me.santio.factory.web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import lombok.Getter;
import lombok.Setter;
import me.santio.factory.FactoryLib;

public class PackWebpage {
    
    @Setter @Getter private static String ip = "127.0.0.1";
    @Setter @Getter private static int port = 3000;
    private static HttpServer httpServer;
    
    public static void start() {
        try {
            httpServer = Vertx.vertx().createHttpServer();
            httpServer.requestHandler(httpServerRequest -> httpServerRequest.response().sendFile(getFileLocation()));
            httpServer.listen(port);
        } catch(Exception e) {
            FactoryLib.getInstance().getLogger().severe("Failed to create webserver! Are you sure I can bind to "+ip+":"+port+"?");
            e.printStackTrace();
        }
    }
    
    public static void close() {
        httpServer.close();
    }
    
    private static String getFileLocation() {
        return FactoryLib.getInstance().getDataFolder().toPath().resolve("generated/pack.zip").toFile().getAbsolutePath();
    }
    
}
