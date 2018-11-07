package servlets.s.price.my_service;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityCategoryShop;
import spring.entity.EntityEmployee;
import spring.entity.EntityPrice;
import spring.interfaces.CategoryShopDao;
import spring.interfaces.EmployeeDao;
import spring.interfaces.PriceDao;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PriceServletService {

    private EmployeeDao employeeDao;
    private PriceDao priceDao;
    private CategoryShopDao categoryShopDao;

    public PriceServletService(WebApplicationContext ctx) {

        employeeDao = ctx.getBean("jpaEmployee", EmployeeDao.class);
        priceDao = ctx.getBean("jpaPrice",PriceDao.class);
        categoryShopDao = ctx.getBean("jpaCategoryShop",CategoryShopDao.class);
    }

    public void deleteAllFunction(long shopId, long priceId) {

        List<EntityEmployee> listEmployees = employeeDao.selectByShopId(shopId);

        if (listEmployees == null || listEmployees.size() == 0) {

            return;
        }

        for (EntityEmployee employee : listEmployees) {

            String functionS = deleteFunction(employee.getFunction(),priceId);

            if (functionS != null) {

                employee.setFunction(functionS);

                employeeDao.save(employee);
            }
        }
    }

    private String deleteFunction(String functionS, long priceId) {

        JSONArray oldFunctionArrayJ = new JSONArray(functionS);

        Set<Long> setLong = new HashSet<>();

        for (int i = 0; i < oldFunctionArrayJ.length(); i++) {

            setLong.add(oldFunctionArrayJ.getLong(i));
        }

        setLong.remove(priceId);

        JSONArray newFunctionArrayJ = new JSONArray(setLong);

        if (oldFunctionArrayJ.length() == newFunctionArrayJ.length()) {

            return null;
        }

        return newFunctionArrayJ.toString();
    }

    public void deleteAllPricesOnCategory(long shopId,long categoryId) {

        EntityCategoryShop categoryShop = categoryShopDao.selectById(categoryId);

        List<EntityPrice> listPrice = priceDao.findAllByCategoryShop(categoryShop);

        for (EntityPrice price : listPrice) {

            deleteAllFunction(shopId,price.getId());

        }

        priceDao.deleteAllByCategoryShop(categoryShop);
    }
}
