package Models;

import org.json.JSONArray;

public class Message {
    private int type;
    private String title;
    private String body;
    private JSONArray servers;
    private long phone;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public JSONArray getServers() {
        return servers;
    }

    public void setServers(JSONArray servers) {
        this.servers = servers;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
