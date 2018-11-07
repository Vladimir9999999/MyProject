package servlets.s.management_employee.my_service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityAppraisal;
import spring.interfaces.AppraisalDao;
import java.sql.Timestamp;
import java.util.List;

public class AppraisalServletService {

    private long shopId;
    private long employeeId;

    private AppraisalDao appraisalDao;


    public AppraisalServletService(long shopId, long employeeId, WebApplicationContext ctx) {


        appraisalDao = ctx.getBean("jpaAppraisal", AppraisalDao.class);

        this.shopId = shopId;
        this.employeeId = employeeId;

    }

    public EntityAppraisal save(JSONObject appraisalJ, int type) {

        EntityAppraisal appraisal = new EntityAppraisal();
        appraisal.setShopId(shopId);
        appraisal.setEmployeeId(employeeId);

        try {
            appraisal.setDate(Timestamp.valueOf(appraisalJ.getString("date")));
        } catch (Exception e) {
            appraisal.setDate(Timestamp.valueOf(appraisalJ.getString("date") + " 00:00:00"));
        }

        appraisal.setComment(appraisalJ.getString("comment"));
        appraisal.setType(type);

        if (appraisalJ.has("id")) {
            appraisal.setId(appraisalJ.getLong("id"));
        }

        appraisalDao.save(appraisal);

        return appraisal;

    }

    public JSONArray getAppraisalArrayJ(List<EntityAppraisal> listAppraisal, int type) {


        JSONArray appraisalArrayJ = new JSONArray();

        for (EntityAppraisal appraisal : listAppraisal) {

            JSONObject appraisalJ = new JSONObject(appraisal);

            if (appraisal.getType() == type) {

                appraisalJ.remove("type");
                appraisalJ.remove("shopId");
                appraisalJ.remove("employeeId");
                appraisalArrayJ.put(appraisalJ);
            }
        }

        return appraisalArrayJ;

    }
}
