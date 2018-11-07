package utils;

import java.util.ArrayList;
import java.util.Arrays;

public class ServersActual {

    public static ArrayList<String> serversS = new ArrayList<>(Arrays.asList("https://zcor.ru:8100/ice/s/", "http://94.255.72.28:8080/ice/s/"));

    public static void addServerS(String server) {
        serversS.remove(server);
        serversS.add(server);
    }


    public static void changePriorityS(String badServer){
        if(serversS.remove(badServer)){
            serversS.add(badServer);
        }
    }


    public static void deleteServerS(String server){
        serversS.remove(server);
    }

}
