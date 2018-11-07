package messagers;

import Models.Message;
import constant.СonstantMessage;
import interfaces.Messager;
import org.json.JSONArray;
import org.json.JSONObject;
import spring.entity.EntitySession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class GoogleMessager implements Messager {

    private static String applicationGoogTocken =  "AAAAoTpNSEs:APA91bFOcxYTZT94BibLiWwovEgWyJYyqFRBLquhoM_t8fF8Z8OSRUZIbwPOGmuV7yLLIZCQRAqhvXCy2pUt7p0F7zj1diXTOxBprKlquFrJqJOQ4t0Ny-IHbupyeH1by06pD7AohNIdmMvHQio0XeTN4ZK2jnefrQ";
    HttpURLConnection conn;
    public GoogleMessager() {
        URL url = null;
        try {
            url = new URL("https://fcm.googleapis.com/fcm/send");

             conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "key=" + applicationGoogTocken);

            conn.setDoOutput(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sendMessages(List<EntitySession> sessions, Message message) {

        int responseCode = 0;
        try {


            JSONObject input = new JSONObject();
            JSONObject messageJ = formatMessage(message);
            JSONArray adress = new JSONArray();
            for(EntitySession session:sessions){
                adress.put(session.getGoogleTocken());
            }


            input.put("registration_ids",adress)
                    .put("priority","high")
                    .put("sound","default")
                    .put("data",messageJ);

            send(input);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void sendMessage(EntitySession session, Message message) {

        int responseCode = 0;
        try {

            JSONObject input = new JSONObject();
            JSONObject messageJ = formatMessage(message);

            input.put("to", session.getGoogleTocken())
                    .put("priority", "high").put("data", messageJ);

            if (message.getType() != СonstantMessage.SERVERS) {
                input.put("sound", "default");
            }


            send(input);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendAllMessage(Message message) throws MalformedURLException {
        int responseCode = 0;
        try {
            JSONObject input = new JSONObject();
            JSONObject messageJ = formatMessage(message);
            input.put("to", "/topics/ALL")

                    .put("priority", "high")
                    .put("sound", "default")
                    .put("data", messageJ);

            send(input);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private JSONObject formatMessage(Message message){

        JSONObject messageJ = new JSONObject();
        switch (message.getType()) {
            case СonstantMessage.MESSAGE:
                messageJ.put("title", message.getTitle()).put("body", message.getBody());
                break;

            case СonstantMessage.PHONE:
                messageJ.put("title", message.getTitle())
                        .put("body", message.getBody())
                        .put("phone", message.getPhone());
                break;
            case СonstantMessage.SERVERS:
                messageJ.put("servers", message.getServers());
                break;
        }
        return messageJ;
    }

    private void send(JSONObject input) throws IOException {
            int responseCode =0;


            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(input.toString().getBytes());
            os.flush();
            os.close();

            try {
                responseCode = conn.getResponseCode();
            }catch (Exception e){}

             System.out.println("Post parameters : " + input);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());
    }

}
