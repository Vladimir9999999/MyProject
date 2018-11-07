package servlets.s.basket.my_service;

import org.json.JSONArray;
import spring.entity.EntityBasket;
import java.util.List;

public class BasketServletService {

    public JSONArray getBasketsArrayJ(List<EntityBasket> listBaskets) {

        JSONArray basketsArrayJ = new JSONArray();

        for (EntityBasket basket : listBaskets) {

            JSONArray basketArrayJ = new JSONArray(basket);

            basketsArrayJ.put(basketArrayJ);
        }

        return basketsArrayJ;
    }
}
