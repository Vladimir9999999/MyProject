package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityArticle;
import spring.entity.EntityParameters;
import spring.entity.EntityPrice;
import spring.interfaces.ArticleDao;
import spring.interfaces.PriceDao;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ArticleService {

    private ArticleDao articleDao;
    private PriceDao priceDao;

    public ArticleService(WebApplicationContext ctx) {

        articleDao = ctx.getBean("jpaArticle", ArticleDao.class);
        priceDao = ctx.getBean("jpaPrice", PriceDao.class);
    }

    public boolean saveArticles(long priceId, JSONArray articlesArrayJ) {

        boolean visible = true;
        String name;
        int length;
        int count;
        double price;

        List<EntityArticle> listArticle = new LinkedList<>();

        for (int i = 0; i < articlesArrayJ.length(); i++) {

            JSONObject articleJ = articlesArrayJ.getJSONObject(i);

            EntityArticle article;

            if (articleJ.has("id")) {

                article = articleDao.selectById(articleJ.getLong("id"));

                if (article == null) {

                    return false;
                }

            } else {
                article = new EntityArticle();
            }

            EntityPrice entityPrice = priceDao.selectById(priceId);

            if (entityPrice == null) {
                return false;
            }

            article.setEntityPrice(entityPrice);

            if (articleJ.has("visible")) {
                visible = (articleJ.getBoolean("visible"));
            }

            article.setVisible(visible);

            if (articleJ.has("name")) {
                name = (articleJ.getString("name"));
                article.setName(name);
            }


            if (articleJ.has("count")) {
                count = (articleJ.getInt("count"));
                article.setCount(count);
            }

            if (articleJ.has("price")) {
                price = (articleJ.getDouble("price"));
                article.setPrice(price);
            }

            listArticle.add(article);
        }

        articleDao.saveAll(listArticle);

        return true;
    }

    public JSONArray articlesToJSONArray(Set<EntityArticle> entityArticles) {

        JSONArray articlesJ = new JSONArray();

        for (EntityArticle article : entityArticles) {

            JSONObject articleJ = new JSONObject();
            articleJ.put("name", article.getName());
            articleJ.put("price", article.getPrice());
            articleJ.put("parameters",parametersToJsonArray(article.getParameters()));
            //articleJ.put("length", article.getLength());
            articleJ.put("id", article.getId());
            articlesJ.put(articleJ);

        }

        return articlesJ;
    }


    private JSONArray parametersToJsonArray(Set<EntityParameters> parametersSet){
        JSONArray result = new JSONArray();
        if(parametersSet == null || parametersSet.size()==0){
            return null;
        }
        for(EntityParameters parameters: parametersSet){

            JSONObject partJ = new JSONObject();
            partJ.put("type",parameters.getType());
            partJ.put("value",parameters.getValue());
            partJ.put("name",parameters.getName());
            result.put(partJ);

        }
        return result;
    }
}
