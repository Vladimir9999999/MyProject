package interfaces;

import Models.Message;
import spring.entity.EntitySession;
import java.net.MalformedURLException;
import java.util.List;

public interface Messager {

    void  sendMessages(List<EntitySession> sessions, Message message);

    void sendMessage(EntitySession session, Message message);

    void sendAllMessage(Message message) throws MalformedURLException;
}
