package servlets.s.catalog.my_service;

import Models.CategoryYML;
import Models.ProductYML;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.*;
import spring.interfaces.*;
import utils.HtmlLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CatalogServletService {

    private ProductDao productDao;
    private EmployeeDao employeeDao;

    private CategoryServiceDao serviceDao;
    private CategoryShopDao categoryShopDao;
    private EntityShop entityShop;
    private ArticleDao articleDao;

    private PriceDao priceDao;

    public CatalogServletService(WebApplicationContext ctx, EntityShop entityShop) {

        this.entityShop = entityShop;
        priceDao = ctx.getBean("jpaPrice",PriceDao.class);
        productDao = ctx.getBean("jpaProduct", ProductDao.class);
        employeeDao = ctx.getBean("jpaEmployee", EmployeeDao.class);

        articleDao = ctx.getBean("jpaArticle",ArticleDao.class);
        serviceDao = ctx.getBean("jpaCategoryService", CategoryServiceDao.class);
        categoryShopDao = ctx.getBean("jpaCategoryShop", CategoryShopDao.class);
    }

    public void saveCat(List<CategoryYML> categoryYMLList, long parent , long ymlParent){

        for(CategoryYML categoryYML: categoryYMLList){

            if(categoryYML.getParent()== ymlParent) {

                EntityCategoryService entityCategoryService = new EntityCategoryService();
                entityCategoryService.setName(categoryYML.getName());

                EntityCategoryShop entityCategoryShop = new EntityCategoryShop();

                entityCategoryShop.setCategoryServiceByCategory(entityCategoryService);
                entityCategoryShop.setParent(parent);
                entityCategoryShop.setShopId(entityShop.getId());

                saveCategoryShop(entityCategoryShop);
                List<ProductYML> productYMLList = categoryYML.getProductsYML();
                savePrice(productYMLList,entityCategoryShop);

                saveCat(categoryYMLList, entityCategoryShop.getId(),categoryYML.getId());

            }
        }

    }



    private void savePrice( List<ProductYML> productYMLList , EntityCategoryShop entityCategoryShop) {

        List<EntityPrice> priceProducts = new ArrayList<>();

        for (ProductYML productYML : productYMLList) {

            EntityProduct product = productDao.selectByName(productYML.getName());
            if (product == null) {

                product = new EntityProduct();
                product.setName(productYML.getName());
                product.setDescriptionl(productYML.getDescription());
                productDao.save(product);

            }
            String urlPicture = productYML.getUrlPicture();

            EntityPrice entityPriceProduct = new EntityPrice();

            entityPriceProduct.setCategoryShop(entityCategoryShop);
            entityPriceProduct.setReserve(0);

            entityPriceProduct.setProductByProduct(product);
            entityPriceProduct.setShopId(entityShop.getId());

            entityPriceProduct.setVisible(true);

            EntityArticle entityArticle = new EntityArticle();
            entityArticle.setPrice(productYML.getPrice());
            articleDao.save(entityArticle);

            Set<EntityArticle> entityArticles = new HashSet<>();

            entityArticles.add(entityArticle);

            entityPriceProduct.setEntityArticles(entityArticles);

            priceProducts.add(entityPriceProduct);
            priceDao.save(entityPriceProduct);

            HtmlLoader htmlLoader = new HtmlLoader();

            String filePath = System.getProperty("upload.dir")+"/products/"+entityPriceProduct.getProductByProduct().getId()+".jpg";
            File path = new File(System.getProperty("upload.dir")+"/products/");
            if(!path.exists()){
                path.mkdirs();
            }

            System.out.println();
            htmlLoader.loadFile(filePath,productYML.getUrlPicture());


        }
    }

    private EntityCategoryShop saveCategoryShop(EntityCategoryShop categoryShop) {

        EntityCategoryService entityCategoryService = categoryShop.getCategoryServiceByCategory();

        EntityCategoryService entityCategoryServiceLoad = serviceDao.findByName(entityCategoryService.getName());

        if(entityCategoryServiceLoad == null){
            entityCategoryServiceLoad = serviceDao.save(entityCategoryService);

        }
        //todo проверка на существование
        categoryShop.setCategoryServiceByCategory(entityCategoryServiceLoad);

        return categoryShopDao.save(categoryShop);

    }

}
