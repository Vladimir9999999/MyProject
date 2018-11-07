package sockets;

import dao.ProductDao;
import org.json.JSONArray;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;

@ServerEndpoint("/searsh")
public class SearshProduct {

    public SearshProduct() {
        System.out.println("class loaded " + this.getClass());
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.printf("Session opened, id: %s%n", session.getId());
        try {
            session.getBasicRemote().sendText("Hi there, we are successfully connected.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.printf("SaveMessage received. Session id: %s SaveMessage: %s%n",
                session.getId(), message);
        try {

            ProductDao productDao = new ProductDao();
            List<String> names = productDao.selectNamesByFragment(message);
            JSONArray namesJ = new JSONArray(names);
            session.getBasicRemote().sendText(namesJ.toString());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
        System.out.printf("Session closed with id: %s%n", session.getId());
    }
}