package utils;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityArticle;
import spring.entity.EntityEmployee;
import spring.interfaces.ArticleDao;
import spring.interfaces.EmployeeDao;
import java.util.List;

public class EmployeeService {

    private ArticleDao articleDao;

    public EmployeeService(WebApplicationContext ctx) {

        articleDao = ctx.getBean("jpaArticle",ArticleDao.class);
    }

    public boolean validArticles(EntityEmployee employee, JSONArray articlesIdArrayJ) {

        boolean flag = false;

        JSONArray functionArrayJ = new JSONArray(employee.getFunction());

        List<EntityArticle> listArticle = getListArticles(articlesIdArrayJ);

        if (listArticle == null) {

            return false;
        }

        for (EntityArticle article : listArticle) {

            long priceId = article.getEntityPrice().getId();

            if (check(priceId, functionArrayJ)) {

                flag = true;

            } else {

                return false;
            }
        }

        return flag;
    }

    private boolean check(long priceId, JSONArray functionArrayJ) {

        for (int i = 0; i < functionArrayJ.length(); i++) {

            long function = functionArrayJ.getLong(i);

            if (priceId == function) {

                return true;
            }
        }

        return false;
    }

    private List<EntityArticle> getListArticles(JSONArray articlesIdArrayJ) {

        List<EntityArticle> listArticles = null;

        for (int i = 0; i < articlesIdArrayJ.length(); i++) {

            long articleId = articlesIdArrayJ.getLong(i);

            listArticles = articleDao.findAllById(articleId);

        }

        return listArticles;
    }
}
