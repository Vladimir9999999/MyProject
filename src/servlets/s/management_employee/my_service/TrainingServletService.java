package servlets.s.management_employee.my_service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityTraining;
import spring.interfaces.TrainingDao;
import utils.DatesUtill;
import java.sql.Timestamp;
import java.util.List;

public class TrainingServletService {

    private long shopId;
    private long employeeId;
    private WebApplicationContext ctx;

    private TrainingDao trainingDao;

    public TrainingServletService(long shopId, long employeeId, WebApplicationContext ctx) {

        this.shopId = shopId;
        this.employeeId = employeeId;
        this.ctx = ctx;

        trainingDao = ctx.getBean("jpaTraining", TrainingDao.class);
    }

    public JSONObject save(JSONObject trainingJ) {

        JSONObject retJ = new JSONObject();

        EntityTraining training = new EntityTraining();

        training.setShopId(shopId);
        training.setEmployeeId(employeeId);

        training.setDays(trainingJ.getInt("days"));

        training.setMinutes(trainingJ.getInt("minutes"));

        if (trainingJ.has("comment")) {
            training.setComment(trainingJ.getString("comment"));
        }

        try {
            training.setDate(Timestamp.valueOf(trainingJ.getString("date") + " 00:00:00"));
        } catch (Exception e) {
            training.setDate(Timestamp.valueOf(trainingJ.getString("date")));
        }

        if (trainingJ.has("id")) {

            training.setId(trainingJ.getLong("id"));

            trainingDao = ctx.getBean("jpaTraining", TrainingDao.class);

            EntityTraining entityTraining = trainingDao.findByIdAndShopId(training.getId(), training.getShopId());

            if (entityTraining != null) {

                try {
                    training.setDate(Timestamp.valueOf(trainingJ.getString("date") + " 00:00:00"));
                } catch (Exception e) {
                    training.setDate(Timestamp.valueOf(trainingJ.getString("date")));
                }

            } else {
                return retJ.put("error", "id или shopId не найден");
            }
        }

        if (checkTimeSave(training)) {

            trainingDao.save(training);

            return retJ.put("id", training.getId());

        } else {

            return retJ.put("error", "Запись на это время уже существует");
        }

    }

    public boolean checkTimeSave(EntityTraining training) {

        trainingDao = ctx.getBean("jpaTraining", TrainingDao.class);

        int fiveMinutes = DatesUtill.getMicrosecondsFiveMinutes(training.getMinutes());

        int days = DatesUtill.getMicrosecondsDays(training.getDays());

        Timestamp startDateTraining = training.getDate();

        Timestamp startDateTrainingPlus = new Timestamp(startDateTraining.getTime() + 2);

        Timestamp endDateTraining = new Timestamp(startDateTrainingPlus.getTime() + days + fiveMinutes);

        int recount = trainingDao.countByPeriod(endDateTraining, startDateTrainingPlus, employeeId);

        List<EntityTraining> listLastTraining = trainingDao.findByLastTraining(startDateTrainingPlus, employeeId);

        if (listLastTraining.size() != 0) {

            EntityTraining lastTraining = listLastTraining.get(0);

            int lastFiveMinutes = DatesUtill.getMicrosecondsFiveMinutes(lastTraining.getMinutes());

            int lastDays = DatesUtill.getMicrosecondsDays(lastTraining.getDays());

            Timestamp startDateLastTraining = lastTraining.getDate();

            Timestamp endDateLastTraining = new Timestamp(startDateLastTraining.getTime() + lastDays + lastFiveMinutes);

            return (startDateTrainingPlus.getTime() >= endDateLastTraining.getTime() && recount == 0);

        }

        return true;
    }

    public JSONArray getArrayTraining(List<EntityTraining> listTraining) {

        JSONArray trainingArrayJ = new JSONArray();

        for (EntityTraining training : listTraining) {

            JSONObject trainingJ = new JSONObject(training);

            trainingJ.remove("shopId");
            trainingJ.remove("employeeId");

            trainingArrayJ.put(trainingJ);
        }

        return trainingArrayJ;
    }
}
