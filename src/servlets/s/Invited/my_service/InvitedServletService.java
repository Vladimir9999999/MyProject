package servlets.s.Invited.my_service;

import org.json.JSONArray;
import org.json.JSONObject;
import spring.entity.EntityUserShops;
import java.util.List;

public class InvitedServletService {

    public JSONArray recursive(long ref , List<EntityUserShops> listUsersShops) {

        JSONArray userShopsArrayJ = new JSONArray();

        for (EntityUserShops entityUserShops : listUsersShops) {

            if (entityUserShops.getRef() == ref) {

                JSONObject us = new JSONObject(entityUserShops);

                us.put("ref",recursive(entityUserShops.getUserId(), listUsersShops));

                userShopsArrayJ.put(us);
            }
        }

        return userShopsArrayJ;
    }
}
