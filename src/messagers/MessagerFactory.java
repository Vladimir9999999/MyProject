package messagers;

import Models.Message;
import interfaces.Messager;
import spring.entity.EntitySession;

import java.util.List;

public class MessagerFactory {

    public static void sendMessage(List<EntitySession> adresses, Message message) {

        for (EntitySession adres : adresses) {

            Messager messager;

            if (adres.getAppleTocken() != null) {

                messager = new AppleMessager();

                messager.sendMessage(adres, message);
            }

            if (adres.getGoogleTocken() != null) {

                messager = new GoogleMessager();

                messager.sendMessage(adres, message);

            }
        }
    }
}
