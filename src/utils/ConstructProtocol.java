package utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class ConstructProtocol {

    private JSONObject body = new JSONObject();


    public ConstructProtocol(String body) {
        JSONObject s = new JSONObject();
        s.put("case",body);
        this.body.put( "status", s);
    }


    public JSONObject addError(JSONObject r , String error){

        r.getJSONObject("status").put("error",error);
        return r;

    }
    public void addBody(JSONObject response){

        body.put( "jsonBody" , response);

    }
    public void addBody(JSONArray response){
        System.out.println(response);
        body.put( "jsonBody" , response);


    }


    public JSONObject getStatus() {
        return body;
    }

    public void setStatus(JSONObject status) {
        this.body = status;
    }
}
