package messagers;

import Models.Message;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsNotification;
import com.notnoop.apns.ApnsService;
import constant.СonstantMessage;
import interfaces.Messager;
import spring.entity.EntitySession;

import java.util.ArrayList;
import java.util.List;

public class AppleMessager implements Messager {

    String filePath = System.getProperty("jboss.modules.dir");
    ApnsService service =  APNS.newService()
            .withCert(filePath+"/xxx.p12","89384718669")
            .withAppleDestination(true)
            .build();


    @Override
    public void sendMessages(List<EntitySession> sessions, Message message) {
        List<String> tockens  = new ArrayList<>();

        for(EntitySession session: sessions){
            tockens.add(session.getAppleTocken());
        }

        String mess= formatter(message);

        List<ApnsNotification> notifications = (List<ApnsNotification>) service.push(tockens,mess);
    }



    @Override
    public void sendMessage(EntitySession session, Message message) {

        List<String> tockens  = new ArrayList<>();

        tockens.add(session.getAppleTocken());


        String mess= formatter(message);

        service.push(tockens,mess);
    }

    @Override
    public void sendAllMessage(Message message) {

    }

    private String formatter(Message message){
        String mess = null;

        switch (message.getType()) {

            case СonstantMessage.MESSAGE:

                mess = APNS.newPayload().alertTitle(message.getTitle()).alertBody(message.getBody()).sound("default").build();

                break;

            case СonstantMessage.PHONE:

                mess = APNS.newPayload().alertTitle(message.getTitle()).sound("default")
                        .alertBody(message.getBody())
                        .customField("phone", message.getPhone()).build();

                break;
            case СonstantMessage.SERVERS:

                mess = APNS.newPayload().customField("servers", message.getServers()).forNewsstand().build();

                break;

        }

        return mess;

    }

}
