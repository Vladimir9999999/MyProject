package utils;

import Models.Employee;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import servlets.ShopServletService;
import spring.entity.*;
import spring.interfaces.*;
import javax.servlet.http.Part;
import java.sql.Timestamp;
import java.util.*;

public class EmployeeManager {

    private int sheduleType;

    private String filepath;
    private String post;

    private ScheduleDao scheduleDao;
    private PriceDao priceDao;
    private EmployeeDao employeeDao;
    private PersonalDao personalDao;
    private TurnDao turnDao;
    private TaskListDao taskDao;
    private DeviationsDao deviationsDao;
    private WebApplicationContext ctx;
    private OrdersService ordersService;

    public EmployeeManager(WebApplicationContext ctx, String filepath) {

        this.ctx = ctx;
        scheduleDao = ctx.getBean("jpaSchedule", ScheduleDao.class);
        employeeDao = ctx.getBean("jpaEmployee", EmployeeDao.class);
        personalDao = ctx.getBean("jpaPersonalDaoService", PersonalDao.class);
        turnDao = ctx.getBean("jpaTurn", TurnDao.class);
        taskDao = ctx.getBean("jpaTaskList", TaskListDao.class);
        deviationsDao = ctx.getBean("jpaDeviations", DeviationsDao.class);
        ordersService = new OrdersService(ctx);
        priceDao = ctx.getBean("jpaPrice",PriceDao.class);

        this.filepath = filepath;
    }

    public JSONArray createTurns(JSONArray turnsJ){

        ArrayList<EntityTurn> trn = new ArrayList<>();
        JSONArray turnsIds = new JSONArray();

        for (int i = 0; i < turnsJ.length(); i++) {

            EntityTurn turn = new EntityTurn();
            JSONObject turnJ = turnsJ.getJSONObject(i);

            if (turnJ.has("turn_id")) {

                int turn_id = turnsJ.getJSONObject(i).getInt("turn_id");

                turn.setId(turn_id);
                turnsIds.put(new JSONObject().put("turn_id",turn.getId()));

            } else {

                turn.setBeginTime(DatesUtill.parameterToInt(turnJ.getInt("sH"), turnJ.getInt("sM")));
                turn.setEndTime(DatesUtill.parameterToInt(turnJ.getInt("fH"), turnJ.getInt("fM")));
                turn.setBeginLunch(DatesUtill.parameterToInt(turnJ.getInt("sLH"), turnJ.getInt("sLM")));
                turn.setEndLunch(DatesUtill.parameterToInt(turnJ.getInt("fLH"), turnJ.getInt("fLM")));


                int indexArray = trn.indexOf(turn);

                if (indexArray != -1) {

                    turn.setId(trn.get(indexArray).getId());

                }else {
                    EntityTurn t = turnDao.selectByTurn(turn);
                    if(t == null) {
                        turnDao.save(turn);
                    }else {
                        turn = t;
                    }
                    trn.add(turn);
                }

                turnsIds.put(new JSONObject().put("turn_id",turn.getId()));

            }
        }
        return turnsIds;
    }

    public EntitySchedule createSchedule(JSONArray turnsIds ){

        EntitySchedule entitySchedule = new EntitySchedule();
        entitySchedule.setScheduleType(String.valueOf(sheduleType));
        entitySchedule.setValue(turnsIds.toString());


        EntitySchedule s = scheduleDao.selectByShedule(entitySchedule);

        if(s == null) {
            scheduleDao.save(entitySchedule);
        }else {
            entitySchedule= s;
        }
        return entitySchedule;
    }

    public EntitySchedule createSchedule(JSONArray turnsIds, Timestamp createData, int weekend){

        EntitySchedule entitySchedule = new EntitySchedule();

        entitySchedule.setScheduleType(String.valueOf(sheduleType));
        JSONObject value = new JSONObject();

        value.put("turns",turnsIds)
                .put("create_data",createData)
                .put("weekend",weekend);

        entitySchedule.setValue(value.toString());

        EntitySchedule s = scheduleDao.selectByShedule(entitySchedule);

        if(s == null) {
            scheduleDao.save(entitySchedule);
        }else {

            entitySchedule= s;

        }
        return entitySchedule;

    }

    public EntitySchedule createSchedule(int sheduleType){

        EntitySchedule schedule = new EntitySchedule();

        schedule.setScheduleType(String.valueOf(sheduleType));
        scheduleDao.save(schedule);

        return schedule;

    }

    public EntityEmployee createEmployee(JSONArray services, long shopId, EntitySchedule schedule, EntityPersonalData personalData){

        EntityEmployee employee = new EntityEmployee();
        employee.setName(personalData.getName());

        employee.setPost(post);

        if(personalData.getEmployeeId()!=null) {

            employee.setId(personalData.getEmployeeId());

        }

        employee.setFunction(services.toString());
        employee.setPrivilege((short) 255);
        employee.setShopId(shopId);
        employee.setScheduleByScheduleId(schedule);

        employeeDao.save(employee);

        personalData.setEmployeeId(employee.getId());



        personalData.setEmployeeId(employee.getId());
        personalData.setId(employee.getId());
        personalDao.save(personalData);

        return employee;
    }

    public void savePhoto(Part filePart, long shopId, long employeeId) throws Exception {
        if(filepath == null){
            throw new Exception();
        }
        if (filePart!=null) {

            FilesUtil.setImageName(String.valueOf(employeeId));
            FilesUtil.saveLogo(filePart,filepath);
        }
    }

    public void setSheduleType(int sheduleType) {
        this.sheduleType = sheduleType;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public JSONArray selectEmployeeTask(long shopId, long employeeId){



        List<EntityTaskList> myTask;
        GregorianCalendar gc = new GregorianCalendar();

        Timestamp thisDate = new Timestamp(new Date().getTime());

        myTask = taskDao.findByEmployeeId(employeeId,thisDate);

        EntityTaskList firstOrder = taskDao.findOneBeforePlannedTime(thisDate,employeeId);
        if(firstOrder != null) {

            long ft = firstOrder.getTime().getTime()+(firstOrder.getLength()*60*5000);

            if(ft >thisDate.getTime()) {
                myTask.add(0, firstOrder);
            }
        }

        JSONArray myTaskJ = new JSONArray();
        for (EntityTaskList taskList: myTask){
            JSONObject taskJ = new JSONObject();

            taskJ.put("planned_time",taskList.getTime().getTime());
            taskJ.put("length",taskList.getLength());

            myTaskJ.put(taskJ);
        }

        return myTaskJ;

    }

    public  JSONObject getEmployeeInterval(long shopId) {


        EntityEmployee entityEmployee;

        List<EntityEmployee> entityEmployees = employeeDao.selectByShopId(shopId);

        entityEmployee =  entityEmployees.get(0);
        JSONArray employeesJ = new JSONArray();



            List<Object> functions = new JSONArray(entityEmployee.getFunction()).toList();

            Employee employeer = new Employee(entityEmployee);


            JSONObject dumpEmployee = new JSONObject();
            JSONObject employee = new JSONObject(employeer.toString());
            JSONObject scheduleJ = new JSONObject();
            System.out.println(employee);

            EntitySchedule schedule = employeer.getScheduleByScheduleId();
            int scheduleType = Integer.parseInt(schedule.getScheduleType());
            JSONArray function = new JSONArray(entityEmployee.getFunction());

            dumpEmployee.put("server_time",new Date().getTime()+TimeZoneShop.getTimeZoneShop(shopId));

            dumpEmployee.put("road_time",ShopServletService.roadTime);

            if(entityEmployee.isTimeDependent()) {

                dumpEmployee.put("tasks", selectEmployeeTask(shopId, entityEmployee.getId()));

            }

            JSONArray turnsJ = new JSONArray();

        JSONObject turnJ = null;
        switch (scheduleType) {
            case 1:
                turnsJ = new JSONArray();
                JSONArray scheduleArr = new JSONArray(schedule.getValue());
                System.out.println(scheduleArr);

                    for (int ii = 0; ii < scheduleArr.length(); ii++) {
                        int turnId = scheduleArr.getJSONObject(ii).getInt("turn_id");
                        EntityTurn entityTurn = turnDao.selectById(turnId);



                        if (entityTurn != null) {

                            turnJ = new JSONObject(entityTurn);
                            turnJ.remove("id");
                        } else {

                            turnJ = new JSONObject();
                            turnJ.put("id", -1);

                        }

                        turnsJ.put(turnJ);

                    }
                    scheduleJ.put("turns", turnsJ);
                    dumpEmployee.put("turn", turnJ);
                    break;

                case 2:

                    JSONObject scheduleVal = new JSONObject(schedule.getValue());
                    System.out.println(scheduleVal);
                    JSONArray turns = scheduleVal.getJSONArray("turns");
                    turnsJ = new JSONArray();
                    for (int ii = 0; ii < turns.length(); ii++) {

                        EntityTurn entityTurn = turnDao.selectById(turns.getJSONObject(ii).getInt("turn_id"));
                        turnJ = new JSONObject(entityTurn);
                        turnJ.remove("id");

                        turnsJ.put(turnJ);

                    }
                    scheduleJ = scheduleVal.put("turns", turnsJ);

                    break;

                case 3:


                    List<EntityDeviations> deviations = deviationsDao.selectByScheduleIdAndDataAfter(schedule.getId(), new Timestamp(new Date().getTime()));

                    turnsJ = new JSONArray();
                    for (EntityDeviations deviation : deviations) {

                        JSONObject devJ = new JSONObject(deviation.getTurns());

                        devJ.put("date", String.format("%1$tF", deviation.getData()));
                        EntityTurn entityTurn = turnDao.selectById(devJ.getInt("turn_id"));
                        devJ.remove("turn_id");

                        turnJ = new JSONObject(entityTurn);
                        turnJ.remove("id");

                        devJ.put("turn", new JSONObject(entityTurn));
                        turnsJ.put(devJ);

                    }
                    if(turnsJ.length()>0) {
                        dumpEmployee.put("turns", turnJ);
                    }
                    break;

            }

        if(turnsJ.length()==0){
                return null;
        }

        return dumpEmployee;
    }

    public List<EntityEmployee> selectAllEmployees( long shopId, JSONArray employesJ){

        List<EntityEmployee > entityEmployeeList = new ArrayList<>();


        for(int i = 0; i<employesJ.length(); i++) {
            EntityEmployee entityEmployee = employeeDao.selectByShopIdAndId(shopId,employesJ.getJSONObject(i).getLong("id"));
            if(entityEmployee == null){
                return null;
            }
            entityEmployeeList.add(entityEmployee);

        }
        return entityEmployeeList;
    }

    public EntityEmployee selectEmployee(long shopId, long employeeId){

        return employeeDao.selectById(employeeId);

    }

    public boolean  validServiceEmployees(JSONArray employeesJ , List<EntityEmployee> entityEmployeeList){

        if(employeesJ.length() != entityEmployeeList.size()){

            return false;

        }
        for (int i = 0; i<entityEmployeeList.size();i++) {
            JSONArray functionClient = employeesJ.getJSONObject(i).getJSONArray("services");
            if(! validServiceEmployee(functionClient,entityEmployeeList.get(i))){
                return false;
            }

        }
        return true;
    }



    public boolean validServiceEmployee(JSONArray servicesArts, EntityEmployee entityEmployee){

        EmployeeService employeeService = new EmployeeService(ctx);

        return employeeService.validArticles(entityEmployee,servicesArts);

    }




    public void addAllPrice(long employeeId){

        EntityEmployee entityEmployee = employeeDao.selectById(employeeId);

        List<EntityPrice> entityPriceList = priceDao.selectByShopId(entityEmployee.getShopId());

        JSONArray allServices = new JSONArray();

        for(EntityPrice entityPrice:entityPriceList){
            allServices.put(entityPrice.getId());
        }

        entityEmployee.setFunction(allServices.toString());

        employeeDao.save(entityEmployee);

    }


    public boolean isWorkDay(EntitySchedule entitySchedule, GregorianCalendar plannedTime){

        EntityTurn turn;
        int turnId;
        switch (entitySchedule.getScheduleType()){

            case "1":
                JSONArray turnsNormal = new JSONArray(entitySchedule.getValue());
                int dayOfWeack = plannedTime.get(Calendar.DAY_OF_WEEK)-1;
                turnId = turnsNormal.getJSONObject(0).getInt("turn_id");
                if(turnId ==  -1) {


                    return false;

                }

                break;

            case "2":
                JSONObject wariableSchedule = new JSONObject(entitySchedule.getValue());

                GregorianCalendar createScheduleDate = new CalendarRussia();
                createScheduleDate.setTimeInMillis(Timestamp.valueOf(wariableSchedule.getString("create_data")).getTime());
                System.out.println(createScheduleDate);
                JSONArray turns = wariableSchedule.getJSONArray("turns");

                int workingDay = turns.length();
                int weekend = wariableSchedule.getInt("weekend");

                int dayOfSchedule = DatesUtill.getFirstDay(createScheduleDate,plannedTime,workingDay,weekend);

                if(dayOfSchedule>workingDay) {

                    return false;
                }

                break;

            case "3":

                GregorianCalendar plannedDay  = new CalendarRussia();
                plannedDay.setTimeInMillis(plannedTime.getTimeInMillis());
                plannedDay.set(Calendar.HOUR_OF_DAY,0);
                plannedDay.set(Calendar.MINUTE,0);
                plannedDay.set(Calendar.SECOND,0);
                plannedDay.set(Calendar.MILLISECOND,0);

                Timestamp timestamp = new Timestamp(plannedDay.getTimeInMillis());
                EntityDeviations entityDeviation = deviationsDao.getByDataAndScheduleId(timestamp,entitySchedule.getId());

                if(entityDeviation==null){
                    return false;

                }
                break;

        }
        return true;

    }

    public boolean isRoadTime(GregorianCalendar plannedTime){
        long roadTimeMs = DatesUtill.getMicrosecondsFiveMinutes(ShopServletService.roadTime);
        Date date = new Date(new Date().getTime()+roadTimeMs-60000);
        return plannedTime.getTimeInMillis() > date.getTime();

    }


    public boolean isFreeSchedule(EntitySchedule entitySchedule, GregorianCalendar plannedTime, int length){
        EntityTurn turn;
        int turnId;



        switch (entitySchedule.getScheduleType()){

            case "1":
                JSONArray turnsNormal = new JSONArray(entitySchedule.getValue());
                int dayOfWeack = plannedTime.get(Calendar.DAY_OF_WEEK)-1;
                turnId = turnsNormal.getJSONObject(0).getInt("turn_id");

                turn = turnDao.selectById(turnId);

                if(ordersService.isFreeSchedule(plannedTime,length,turn)){

                    return false;
                }

                break;

            case "2":

                JSONObject wariableSchedule = new JSONObject(entitySchedule.getValue());

                GregorianCalendar createScheduleDate = new CalendarRussia();
                createScheduleDate.setTimeInMillis(Timestamp.valueOf(wariableSchedule.getString("create_data")).getTime());
                System.out.println(createScheduleDate);
                JSONArray turns = wariableSchedule.getJSONArray("turns");

                int workingDay = turns.length();
                int weekend = wariableSchedule.getInt("weekend");

                int dayOfSchedule = DatesUtill.getFirstDay(createScheduleDate,plannedTime,workingDay,weekend);



                turn = turnDao.selectById(turns.getJSONObject(dayOfSchedule).getInt("turn_id"));

                if(ordersService.isFreeSchedule(plannedTime,length,turn)){

                    return false;
                }

                break;

            case "3":

                GregorianCalendar plannedDay  = new GregorianCalendar();
                plannedDay.setTimeInMillis(plannedTime.getTimeInMillis());
                plannedDay.set(Calendar.HOUR_OF_DAY,0);
                plannedDay.set(Calendar.MINUTE,0);
                plannedDay.set(Calendar.SECOND,0);
                plannedDay.set(Calendar.MILLISECOND,0);

                Timestamp timestamp = new Timestamp(plannedDay.getTimeInMillis());
                EntityDeviations entityDeviation = deviationsDao.getByDataAndScheduleId(timestamp,entitySchedule.getId());

                turnId = new JSONObject(entityDeviation.getTurns()).getInt("turn_id");
                turn = turnDao.selectById(turnId);

                if(ordersService.isFreeSchedule(plannedTime,length,turn)){

                    return false;

                }

                break;

        }
        return true;
    }

}
