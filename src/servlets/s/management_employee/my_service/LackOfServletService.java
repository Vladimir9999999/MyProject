package servlets.s.management_employee.my_service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityLackOf;
import spring.interfaces.LackOfDao;
import utils.DatesUtill;
import java.sql.Timestamp;
import java.util.List;

public class LackOfServletService {

    private long shopId;
    private long employeeId;
    private WebApplicationContext ctx;

    private LackOfDao lackOfDao;

    public LackOfServletService(long shopId, long employeeId, WebApplicationContext ctx) {

        this.shopId = shopId;
        this.employeeId = employeeId;
        this.ctx = ctx;

        lackOfDao = ctx.getBean("jpaLackOf", LackOfDao.class);

    }

    public JSONObject save(JSONObject lackOfJ, int type) {

        JSONObject retJ = new JSONObject();

        int duration = lackOfJ.getInt("duration");

        /*if (duration < 0) {

            return retJ.put("error", "Некорректная длительность");
        }*/

        EntityLackOf lackOf;

        if (lackOfJ.has("id")) {

            lackOf = lackOfDao.findByIdAndShopId(lackOfJ.getLong("id"), shopId);

            if (lackOf == null) {

                return retJ.put("error", "Запись не найдена");
            }

        } else {
            lackOf = new EntityLackOf();
        }

        lackOf.setShopId(shopId);
        lackOf.setEmployeeId(employeeId);

        lackOf.setDuration(duration);

        if (lackOfJ.has("comment")) {
            lackOf.setComment(lackOfJ.getString("comment"));
        }

        lackOf.setType(type);

        try {
            lackOf.setDate(Timestamp.valueOf(lackOfJ.getString("date") + " 00:00:00"));
        } catch (Exception e) {
            lackOf.setDate(Timestamp.valueOf(lackOfJ.getString("date")));
        }

        if (checkTimeSave(lackOf)) {

            lackOfDao.save(lackOf);

            return retJ.put("id", lackOf.getId());

        } else {

            return retJ.put("error", "Запись на это время уже существует");
        }

    }

    public boolean checkTimeSave(EntityLackOf lackOf) {

        lackOfDao = ctx.getBean("jpaLackOf", LackOfDao.class);

        int time = DatesUtill.getMicrosecondsFiveMinutes(lackOf.getDuration());

        Timestamp startDateLackOf = lackOf.getDate();

        Timestamp startDateLackOfPlus = new Timestamp(startDateLackOf.getTime() + 2);

        Timestamp endDateLackOf = new Timestamp(startDateLackOf.getTime() + time);

        int recount = lackOfDao.countByPeriod(endDateLackOf, startDateLackOf, employeeId);

        if (recount != 0) {

            return false;

        } else {

            List<EntityLackOf> listLastLackOf = lackOfDao.findByLastLackOf(startDateLackOfPlus, employeeId);

            if (listLastLackOf.size() != 0) {

                EntityLackOf lastLackOf = listLastLackOf.get(0);

                int lastTime = DatesUtill.getMicrosecondsFiveMinutes(lastLackOf.getDuration());

                Timestamp startDateLastLackOf = lastLackOf.getDate();

                Timestamp endDateLastLackOf = new Timestamp(startDateLastLackOf.getTime() + lastTime);

                return (startDateLackOf.getTime() >= endDateLastLackOf.getTime());

            } else {
                return true;
            }
        }
    }

    public JSONArray getHospitalArrayJ(List<EntityLackOf> listHospital) {

        return getLackOf(listHospital, EntityLackOf.HOSPITAL);
    }

    public JSONArray getPauseArrayJ(List<EntityLackOf> listPause) {

        return getLackOf(listPause, EntityLackOf.PAUSE);
    }

    public JSONArray getTimeOfArrayJ(List<EntityLackOf> listTimeOf) {

        return getLackOf(listTimeOf, EntityLackOf.TIME_OF);
    }

    public JSONArray getVacationArrayJ(List<EntityLackOf> listVacation) {

        return getLackOf(listVacation, EntityLackOf.VACATION);
    }

    private JSONArray getLackOf(List<EntityLackOf> listLackOf, int type) {

        JSONArray lackOfArrayJ = new JSONArray();

        for (EntityLackOf lackOf : listLackOf) {

            JSONObject lackOfJ = new JSONObject(lackOf);
            lackOfJ.remove("type");
            lackOfJ.remove("shopId");
            lackOfJ.remove("employeeId");

            if (lackOf.getType() == type) {
                lackOfArrayJ.put(lackOfJ);
            }

        }

        return lackOfArrayJ;
    }
}
