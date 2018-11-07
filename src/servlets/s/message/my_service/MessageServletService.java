package servlets.s.message.my_service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityMessage;
import spring.entity.EntityUserShops;
import spring.interfaces.MessageDao;
import spring.interfaces.UserShopDao;
import java.util.List;

public class MessageServletService {

    private MessageDao messageDao;
    private UserShopDao userShopDao;
    private long userId;
    private long shopId;

    public MessageServletService(long userId,long shopId,WebApplicationContext ctx) {

        messageDao = ctx.getBean("jpaMessage", MessageDao.class);
        userShopDao = ctx.getBean("jpaUserShop",UserShopDao.class);
        this.userId = userId;
        this.shopId = shopId;
    }

    public JSONArray getMessageArrayJ() {

        EntityUserShops userShops = userShopDao.findByUserIdAndShop(userId,shopId);

        if (userShops == null) {

            return null;
        }

        List<EntityMessage> entityMessageList = messageDao.findAllByEntityUserShopsList(userShops);

        if (entityMessageList == null || entityMessageList.size() == 0) {

            return null;
        }

        JSONArray messageArrayJ = new JSONArray();

        for (EntityMessage message : entityMessageList) {

            JSONObject messageJ = new JSONObject(message);

            messageJ.remove("entityUserShopsList");

            messageArrayJ.put(messageJ);
        }

        return messageArrayJ;
    }

    public void deleteMessages() {

        EntityUserShops userShops = userShopDao.findByUserIdAndShop(userId,shopId);

        messageDao.deleteAllByEntityUserShopsList(userShops);
    }
}
