package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityIndexPageShop;
import spring.interfaces.CategoryShopDao;
import spring.interfaces.IndexPageShopDao;
import spring.interfaces.PriceDao;

public class IndexShopService {

    private IndexPageShopDao indexPageShopDao;
    private String status = null;
    private PriceDao priceDao;
    CategoryShopDao categoryShopDao;
    private long shopId;

    public IndexShopService(WebApplicationContext ctx, long shopId) {

        indexPageShopDao = ctx.getBean("jpaIndexPageShop", IndexPageShopDao.class);
        priceDao = ctx.getBean("jpaPrice", PriceDao.class);
        categoryShopDao = ctx.getBean("jpaCategoryShop",CategoryShopDao.class);
        this.shopId = shopId;
    }

    public boolean saveIndexShop(EntityIndexPageShop entityIndexPageShop) {



         if(isValidValue(entityIndexPageShop.getValue())){
             indexPageShopDao.save(entityIndexPageShop);
             return true;

         }else {
             return false;
         }

    }

    public boolean isValidValue(String valueIndexShop) {
        return true;
        /*try {
            JSONArray valueJ = new JSONArray(valueIndexShop);
            boolean flag = true;

            for (int i = 0; i < valueJ.length() &&flag ; i++) {

                JSONObject element = valueJ.getJSONObject(i);


                if (element.has("type")) {

                    ShopIndexKeys sK = ShopIndexKeys.valueOf(element.getString("type"));

                    switch (sK) {

                        case product:
                            flag =  isValidProduct(element);
                            break;
                        case category:
                            flag = isValidCategory(element);
                            break;
                        case news:
                            flag = isValidNews(element);
                            break;
                        case image:
                            flag = true;
                            break;
                        case label:
                            flag = isValidLabel(element);
                            break;
                    }

                } else {
                    status = "type = null";
                    flag = false;
                }
            }

            return flag;

        } catch (JSONException e) {
            status = e.getMessage();
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            status = "Hеверный тип ячейки";
            return false;
        }*/
    }

    private boolean isValidLabel(JSONObject element){
        if(element.has("text")){
            return true;
        }else {
            status = "text == null";
            return false;
        }
    }

    private boolean isValidNews(JSONObject element){

        if(element.has("title") || element.has("body")) {
            return true;
        }else {
            status = "title == null && body == null";
            return false;
        }
    }

    private boolean isValidCategory(JSONObject element){

        try{

            if(element.has("category_id") && categoryShopDao.existsParent(shopId, element.getLong("category_id"))){
                return true;
            }
            else {
                status = "category_id = null";
            }

        }catch (JSONException e){

            status = e.getMessage();

        }
        return false;

    }

    private boolean isValidProduct(JSONObject element) {

        try {
            if (element.has("product_id") && priceDao.isByShopID(shopId, element.getLong("product_id"))) {

                return true;

            }else {

                status = "product_id = null";
                return false;

            }
        } catch (JSONException e){

            status = e.getMessage();
            return false;
        }
    }


    public String getStatus() {
        return status;
    }
}
