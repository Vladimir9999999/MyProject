package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import servlets.ShopServletService;
import spring.entity.*;
import spring.interfaces.*;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PriceService {

    private CategoryShopDao categoryShopDao;
    private PriceDao priceDao;
    private ProductDao productDao;
    private ArticleDao articleDao;
    private EmployeeDao employeeDao;
    private ParametersDao parametersDao;
    private WebApplicationContext ctx;
    public PriceService(WebApplicationContext ctx) {
        this.ctx = ctx;
        categoryShopDao = ctx.getBean("jpaCategoryShop",CategoryShopDao.class);
        priceDao = ctx.getBean("jpaPrice",PriceDao.class);
        productDao = ctx.getBean("jpaProduct", ProductDao.class);
        articleDao = ctx.getBean("jpaArticle", ArticleDao.class);
        employeeDao = ctx.getBean("jpaEmployee",EmployeeDao.class);
        parametersDao = ctx.getBean("jpaParameters",ParametersDao.class);
    }

    public JSONObject getPriceUser(WebApplicationContext ctx, long shopId){

        return getPrice(ctx,shopId,true);

    }



    public JSONObject getPriceShop(WebApplicationContext ctx, long shopId) {

        return getPrice(ctx,shopId,false);

    }

    private   JSONArray getCategory(WebApplicationContext ctx , long shopId){

        List<EntityCategoryShop> categories =  categoryShopDao.selectByShopId(shopId);

        JSONArray categoriesJ = new JSONArray();

        for (EntityCategoryShop category: categories){
            if(category.getId() != 0){

                JSONObject p = category.toJsonAndPrices();
                categoriesJ.put(p);

            }
        }
        if(categoriesJ.length()==0 ){
            return null;
        }

        return  categoriesJ;

    }

    private JSONArray getServices (WebApplicationContext ctx, long shopId, boolean isVisible){

        List<EntityPrice> prices;
        if(isVisible) {
             prices= priceDao.selectByShopIdIsVisible(shopId);
        }else {
            prices = priceDao.selectByShopId(shopId);
        }
        JSONArray servicesJ = new JSONArray();

        for (EntityPrice price:prices){
            servicesJ.put(productToJSon(price));
        }

        return servicesJ;
    }
    public JSONObject productToJSon(EntityPrice price){
        JSONObject productJ= new JSONObject();
        ArticleService articleService = new ArticleService(ctx);
        EntityProduct product = price.getProductByProduct();
        productJ.put("id",price.getId())
                .put("name",product.getName())
                .put("description",price.getDescription())
                .put("articles",articleService.articlesToJSONArray(price.getEntityArticles()))
                .put("image","http://94.255.72.110:8080/ice/products/"+price.getId()+".jpg")
                .put("visibility",price.isVisible())
                .put("parent",price.getCategoryShop().getId())
                .put("priority",price.getPriority());
        return productJ;
    }

    private JSONObject getPrice(WebApplicationContext ctx, long shopId, boolean isVisible){

        JSONObject priceJ = new JSONObject();

        JSONArray categoriesJ = getCategory(ctx,shopId);
        JSONArray servicesJ = getServices(ctx,shopId,isVisible);


        priceJ.put("categories", categoriesJ);
        priceJ.put("services",servicesJ);
        return priceJ;
    }


    public EntityPrice savePrice(EntityCategoryShop parent,long shopId, JSONObject productJ){

        String productName = productJ.getString("name");



        long id = 0;

        EntityPrice price = null ;

        if(productJ.has("id")){

            id = productJ.getLong("id");
            price = priceDao.selectByShopIdByID(shopId,id);


        }else {

            price = new EntityPrice();
            price.setPriority(priceDao.countByShopID(shopId)+1);

        }
        String description = null;
        if(productJ.has("description")) {
            description = productJ.getString("description");
        }

        EntityProduct product = productDao.selectByName(productName);

        if(product == null){

            product = new EntityProduct();

            product.setName(productName);

            productDao.save(product);

        }

        price.setShopId(shopId);


        price.setDescription(description);
        price.setProductByProduct(product);


        price.setCategoryShop(parent);

        JSONArray articles = productJ.getJSONArray("articles");

        Set<EntityArticle> oldArtSet =price.getEntityArticles();

        if(oldArtSet != null) {
            for (EntityArticle oldArt : oldArtSet) {
                oldArt.setEntityPrice(null);
            }
        }else{
            price.setEntityArticles(new HashSet<>());
        }
        List<EntityArticle> allArt = new ArrayList<>();
        for (int i=0; i<articles.length(); i++) {
            EntityArticle article = null;

            if (articles.getJSONObject(i).has("id")) {

                for(EntityArticle art: price.getEntityArticles()){
                    if(art.getId() == articles.getJSONObject(i).getLong("id")) {
                        article = art;
                    }
                }
                if (article == null){
                    article = new EntityArticle();
                }
            } else {
                article = new EntityArticle();
            }

            float sell = (float) articles.getJSONObject(i).getDouble("price");

            String name = articles.getJSONObject(i).getString("artName");

            if(articles.getJSONObject(i).has("parameters")) {
                JSONArray parameters = articles.getJSONObject(i).getJSONArray("parameters");
                article.setParameters(getParameters(parameters));

            }


            article.setPrice(sell);
            article.setLength(0);

            article.setEntityPrice(price);
            article.setName(name);
            if(oldArtSet!=null) {
                oldArtSet.remove(article);
            }
            allArt.add(article);
            price.getEntityArticles().add(article);
        }
        boolean visibility = productJ.getBoolean("visibility");
        price.setVisible(visibility);


        articleDao.saveAll(new ArrayList<>(price.getEntityArticles()));

        articleDao.deleteAllByEntityPrice(null);

        priceDao.save(price);

        List<EntityEmployee> employeeList = employeeDao.selectByShopId(shopId);
        EntityEmployee employee = employeeList.get(0);

        JSONArray functionsJ= new JSONArray(employee.getFunction());
        functionsJ.put(price.getId());
        employee.setFunction(functionsJ.toString());
        employeeDao.save(employee);


        return price;
    }
    private Set<EntityParameters> getParameters(JSONArray paramsJ){

        Set<EntityParameters> parametersSet = new HashSet<>();

        for(int i=0; i<paramsJ.length(); i++){
            JSONObject partJ = paramsJ.getJSONObject(i);
            EntityParameters entityParameters;
            if (partJ.has("id")) {

                entityParameters = parametersDao.selectById(partJ.getLong("id"));

            }else {

                entityParameters = parametersDao.findTypeAndValue(partJ.getString("type"),partJ.getString("value"));
                if(entityParameters == null){
                    entityParameters = new EntityParameters();
                    entityParameters.setType(partJ.getString("type"));
                    entityParameters.setValue(partJ.getString("value"));

                    if(partJ.has("name")){

                        entityParameters.setName(partJ.getString("name"));

                    }

                    parametersDao.save(entityParameters);
                }

            }
            parametersSet.add(entityParameters);
        }

        return parametersSet;

    }
    public List<EntityPrice> changeProductPriority(JSONArray products, long shopId){
        List<EntityPrice> entityPriceList = new ArrayList<>();
        for (int i = 0; i < products.length(); i++) {

            EntityPrice entityPrice = priceDao.selectByShopIdByID(shopId, products.getLong(i));
            if (entityPrice != null) {

                entityPrice.setPriority(i);
                entityPriceList.add(entityPrice);

            } else {
                return null;
            }
        }
        return entityPriceList;
    }
    public List<EntityCategoryShop> changeCategoryShopPriority(JSONArray categories, long shopId){

        List<EntityCategoryShop> entityCategoryShopList = new ArrayList<>();



        for (int i = 0; i < categories.length(); i++) {

            EntityCategoryShop categoryShop = categoryShopDao.selectByShopIdAndId(shopId, categories.getLong(i));

            if (categoryShop != null) {
                categoryShop.setPriority(i);
                entityCategoryShopList.add(categoryShop);
            } else {
                return null;
            }
        }
        return entityCategoryShopList;
    }

}
