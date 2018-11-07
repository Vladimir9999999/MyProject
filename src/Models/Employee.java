package Models;

import org.json.JSONArray;
import org.json.JSONObject;
import spring.entity.EntityArticle;
import spring.entity.EntityEmployee;
import spring.entity.EntityPersonalData;
import spring.entity.EntityPrice;
import java.util.List;

public class Employee extends EntityEmployee {

    private EntityPersonalData personalData;
    private List<EntityArticle> entityArticle;

    public Employee(EntityEmployee employee) {

        setId(employee.getId());
        setName(employee.getName());
        setFunction(employee.getFunction());
        setScheduleByScheduleId(employee.getScheduleByScheduleId());
        setPrivilege(employee.getPrivilege());
        setShopId(employee.getShopId());
        setPost(employee.getPost());
    }

    public EntityPersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(EntityPersonalData personalData) {
        this.personalData = personalData;
    }

    @Override
    public String toString(){

        JSONObject employee = new JSONObject();
        employee.put("id",getId())
                .put("name",getName());
        if(getPost()!=null) {
                employee.put("post", getPost());
        }else{
            employee.put("post","null");
        }
        int sexI = 0;

        if(personalData!=null) {
            employee.put("surname", personalData.getSurname() + "")
                    .put("patronymic", personalData.getPatronymic())
                    .put("bday", String.format("%1$tF",personalData.getBday()));
            if (personalData.getSex() != null) {

                if (personalData.getSex()) {
                    sexI = 1;
                }

            } else {
                sexI = -1;
            }
        }else {
            sexI=-1;
            employee.put("surname","")
                    .put("patronymic","")
                    .put("bday","0000-00-00");

        }

        if(getScheduleByScheduleId()!=null) {

            if(getScheduleByScheduleId().getScheduleType().equals("1")) {
                JSONObject shed = new JSONObject();
                shed.put("turns", new JSONArray(getScheduleByScheduleId().getValue()));
                employee.put("schedule_type", Integer.parseInt(getScheduleByScheduleId().getScheduleType()))
                        .put("schedule",shed);
            }
            if(getScheduleByScheduleId().getScheduleType().equals("2")){

                employee.put("schedule_type", Integer.parseInt(getScheduleByScheduleId().getScheduleType()))
                        .put("schedule", new JSONObject(getScheduleByScheduleId().getValue()));
            }
            if(getScheduleByScheduleId().getScheduleType().equals("3")){



            }

        }else {
            employee.put("schedule_type", -1)
                    .put("turns", new JSONArray("[-1,-1,-1,-1,-1,-1,-1]"));

        }
        employee.put("my_services",new JSONArray(getFunction()));


        employee.put("sex", sexI);
        return employee.toString();
    }

    public List<EntityArticle> getEntityPrices() {
        return entityArticle;
    }

    public void setEntityPrices(List<EntityArticle> entityArticle) {
        this.entityArticle = entityArticle;
    }
}
