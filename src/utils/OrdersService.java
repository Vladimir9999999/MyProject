package utils;

import Models.Employee;
import Models.Message;
import Models.ZipTask;
import constant.СonstantMessage;
import messagers.MessagerFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import pay.Pay;
import pay.PayFactory;
import servlets.ShopServletService;
import spring.entity.*;
import spring.interfaces.*;

import javax.persistence.EntityTransaction;
import java.sql.Timestamp;
import java.util.*;

public class OrdersService {

    private long marketPlace=0;
    private WebApplicationContext ctx;
    private TaskListDao taskListDao;
    private RemarkDao remarkDao;
    private BillsDao billsDao;
    private OrderDao orderDao;
    private TransactionDao transactionDao;
    private UserShopDao userShopDao;
    private EmployeeDao employeeDao;
    private PriceDao priceDao;
    private ArticleDao articleDao;
    private String errorMessage;
    private static List<ZipTask> journal = new LinkedList<>();

    public static void regJournal(ZipTask zipTask){

        journal.add(zipTask);

    }
    public static void removeElementJournal(ZipTask zipTask){

        journal.remove(zipTask);

    }

    public OrdersService(WebApplicationContext ctx){

        this.ctx = ctx;
        initialization();
    }



    public OrdersService(long marketPlace, WebApplicationContext ctx) {

        this.ctx = ctx;
        this.marketPlace = marketPlace;
        initialization();
    }

    private void initialization(){
        articleDao = ctx.getBean("jpaArticle",ArticleDao.class);
        employeeDao = ctx.getBean("jpaEmployee", EmployeeDao.class);
        taskListDao = ctx.getBean("jpaTaskList", TaskListDao.class);
        remarkDao = ctx.getBean("jpaRemark", RemarkDao.class);
        billsDao = ctx.getBean("jpaBills", BillsDao.class);
        orderDao = ctx.getBean("jpaOrder", OrderDao.class);
        transactionDao = ctx.getBean("jpaTransactions", TransactionDao.class);
        userShopDao = ctx.getBean("jpaUserShop", UserShopDao.class);
        priceDao = ctx.getBean("jpaPrice", PriceDao.class);
    }

    public double getCBUserinOrder(long orderId, long userId){

        BillsDao billsDao = ctx.getBean("jpaBills",BillsDao.class);


        List<EntityBills> entityBillsList = billsDao.selectByOrderId(orderId);
        for (EntityBills bills: entityBillsList){
            switch (bills.getType()){
                case EntityBills.PAY_METHOD_CASHBACK:
                    EntityTransactions transactions = bills.getTransactionsByTranzaction();
                    if(transactions.getPayee() != null && transactions.getPayee()==userId) {

                        return bills.getTransactionsByTranzaction().getSumm();
                    }
                    break;
            }
        }
        return 0;
    }

    public JSONArray ordersToJsonShop(WebApplicationContext ctx, List<EntityOrders> myOrders) {

        UserService userService = new UserService(ctx);


        JSONArray myOrdersJ = new JSONArray();

        for (EntityOrders order : myOrders) {

            JSONObject orderJ = new JSONObject();
            orderJ.put("id", order.getId());
            orderJ.put("status", order.getStatus());
            if(order.getAddress()!= null) {
                orderJ.put("address", new JSONObject(order.getAddress()));
            }
            if(order.getAccountUsers() != null && order.getAccountUsers().getId() != 0) {

                JSONObject client = new JSONObject();
                EntityAccountUsers accountUsers =  order.getAccountUsers();
                if(accountUsers.getId() != 0) {
                    client.put("phone", accountUsers.getLogin());
                    client.put("id", order.getAccountUsers().getId());

                    orderJ.put("client", client);
                }
            }

            orderJ.put("favorite", order.getFavorite());
            orderJ.put("prepay", order.getPrepay());

            orderJ.put("length",order.getLength());
            orderJ.put("summ",order.getSumm());
            List<EntityTaskList> taskLists = order.getTaskListsById();
            JSONArray tasksJ = new JSONArray();

            for(EntityTaskList task: taskLists) {

                JSONObject taskJ = new JSONObject();

                JSONArray services = new JSONArray(task.getServices());

                orderJ.put("planned_time", task.getTime().getTime());
                orderJ.put("check", services);

                tasksJ.put(taskJ);
            }


            myOrdersJ.put(orderJ);
            //orderJ.put("tasks", tasksJ);

        }
        return myOrdersJ;
    }


    public JSONArray getRemark(int remarkType, long adresse) {

        List<EntityRemark> remarks = remarkDao.findByAdresseAndTypeAndMarketplace(adresse, remarkType,marketPlace);

        JSONArray remarksJ =null;
        if(remarks != null && remarks.size()>0) {
            remarksJ = new JSONArray();
            for (EntityRemark remark : remarks) {

                JSONObject remarkJ = new JSONObject();
                remarkJ.put("remark", remark.getMsg());
                remarksJ.put(remarkJ);

            }
        }

        return remarksJ;
    }

    public JSONObject enterPrepayShop(WebApplicationContext ctx, EntityOrders entityOrders,float prepay,int payMethod){

        JSONObject status= new JSONObject();

        if (entityOrders.getAccountUsers()== null){

            entityOrders.setAccountUsers(new EntityAccountUsers());

        }

        if(entityOrders.getAccountUsers().getId() == 0 && payMethod == EntityBills.PAY_METHOD_CASHBACK){

            status.put("error","Прохожие не могут предоплачивать кешбеком");
            return status;

        }

        if (!(payMethod == EntityBills.PAY_METHOD_CASHBACK && entityOrders.getSumm() < entityOrders.getPrepay() + prepay)){

            Pay pay = PayFactory.getPay(ctx, entityOrders, prepay, payMethod);

            double overpayment = entityOrders.getPrepay()+prepay -  entityOrders.getSumm();

            if(overpayment >0){

                EntityUserShops entityUserShops = userShopDao.findByUserIdAndShop(entityOrders.getAccountUsers().getId(), entityOrders.getShopId());

                overpayment += overpayment / 100.00 * entityUserShops.getCashback();

                EntityTransactions overpay = new EntityTransactions();

                overpay.setCurrency(EntityBills.PAY_METHOD_CASHBACK);
                overpay.setDete(new Timestamp(new Date().getTime()));
                overpay.setMarketplace(entityOrders.getShopId());
                overpay.setPayee(entityOrders.getAccountUsers().getId());
                overpay.setSumm(overpayment);

                transactionDao.save(overpay);

                entityUserShops.setCashbackActive((float) (entityUserShops.getCashbackInactive()+overpayment));
                userShopDao.save(entityUserShops);

            }

            EntityTransactions transactions = pay.createTransactionBuy();

            if(pay.getError()!= null){

                status.put("error", "Транзакция отмененна");
                status.put("motive",pay.getError());
                return status;

            }


            EntityBills newBills = new EntityBills();

            newBills.setOrdersByOrderId(entityOrders);
            newBills.setType(payMethod);
            newBills.setTransactionsByTranzaction(transactions);

            billsDao.save(newBills);

            entityOrders.setPrepay(entityOrders.getPrepay() + prepay);
            if(entityOrders.getAccountUsers().getId()==0){
                entityOrders.setAccountUsers(null);
            }
            orderDao.save(entityOrders);

            status.put("error", "ok");


        }else{

            status.put("error", "Сумма оплаты первышает сумму ордера");

        }

        return status;
    }

    public JSONObject  enterPrepay(WebApplicationContext ctx, EntityOrders entityOrders,float prepay,int payMethod){

        JSONObject status= new JSONObject();
        if (entityOrders.getAccountUsers()== null){
            entityOrders.setAccountUsers(new EntityAccountUsers());
        }

        if(entityOrders.getAccountUsers().getId() == 0 && payMethod == EntityBills.PAY_METHOD_CASHBACK){

            status.put("error","Прохожие не могут предоплачивать кешбеком");
            return status;

        }

        if (entityOrders.getSumm() >= entityOrders.getPrepay() + prepay) {


                Pay pay = PayFactory.getPay(ctx, entityOrders, prepay, payMethod);

                EntityTransactions transactions = pay.createTransactionBuy();

                if(pay.getError()!= null){
                    status.put("error", "Транзакция отмененна");
                    status.put("motive",pay.getError());
                    return status;
                }


                EntityBills newBills = new EntityBills();

                newBills.setOrdersByOrderId(entityOrders);
                newBills.setType(payMethod);
                newBills.setTransactionsByTranzaction(transactions);

                billsDao.save(newBills);

                entityOrders.setPrepay(entityOrders.getPrepay() + prepay);
                if(entityOrders.getAccountUsers().getId()==0){
                    entityOrders.setAccountUsers(null);
                }
                orderDao.save(entityOrders);

                status.put("error", "ok");


        }else{

            status.put("error", "Сумма оплаты первышает сумму ордера");

        }

        return status;
    }

    public   boolean isFreeSchedule(GregorianCalendar plannedTime, int length, EntityTurn turn){
        boolean free = false;

        plannedTime.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));

        int startInterval = DatesUtill.parameterToInt(plannedTime);


        int finishInterval = startInterval+length;

        if(startInterval>=turn.getBeginTime() && finishInterval <= turn.getEndTime()){
            free = true;
        }

        return !free;
    }



    public boolean isFreeTime(GregorianCalendar plannedTime, int length, Employee employee){

        if(!employee.isTimeDependent()){

            return true;

        }

        boolean status = true;
        int lengthMs = DatesUtill.getMicrosecondsFiveMinutes(length);
        long finishTime = plannedTime.getTimeInMillis()+lengthMs;

        Timestamp sT = new Timestamp(plannedTime.getTimeInMillis()+60000*2);
        Timestamp fT = new Timestamp(finishTime);

        ZipTask zt  = new ZipTask(fT.getTime(),sT.getTime());

        System.out.println(sT);
        System.out.println(fT);

            for (ZipTask zipTask : journal) {

                if (zipTask.getFinishTime() <= sT.getTime() || (zipTask.getPlannedTime() >= fT.getTime())) {
                    continue;
                } else {
                    System.out.println("Запись найдена в журнале, размер журнала -  " + journal.size());
                    return false;
                }
            }

        regJournal(zt);

        EntityTaskList beforeTask = taskListDao.findOneBeforePlannedTime(sT,employee.getId());

        if(beforeTask != null){

            Timestamp beforeTime = beforeTask.getTime();

            GregorianCalendar finishBefore = new GregorianCalendar();
            finishBefore.setTimeInMillis(beforeTime.getTime()+(beforeTask.getLength()*60000*5));

            if(finishBefore.getTimeInMillis() > plannedTime.getTimeInMillis()){
                status = false;
                removeElementJournal(zt);
            }

        }
        int count = taskListDao.countOderPeriod(sT,fT,employee.getId());

        return  status && count==0;

    }

    public JSONArray fixServices(List<EntityArticle> articles){

        Map<EntityArticle, Integer> countMap = new HashMap<>();

        for (EntityArticle item: articles) {

            if (countMap.containsKey(item))
                countMap.put(item, countMap.get(item) + 1);
            else
                countMap.put(item, 1);
        }
        JSONArray myServices = new JSONArray();

        for (Map.Entry<EntityArticle, Integer> entry : countMap.entrySet()) {

            EntityArticle article = entry.getKey();

            JSONObject service = new JSONObject();

            service.put("id",article.getId());
            //service.put("category",article.getEntityPrice().getCategoryShop().getCategoryServiceByCategory().getName());
            service.put("name",article.getEntityPrice().getProductByProduct().getName());
            service.put("quantity",entry.getValue());
            service.put("price",article.getPrice());

            myServices.put(service);

        }

        return myServices;

    }

    public double deleteOrder(WebApplicationContext ctx, EntityOrders entityOrders){
        DeleteMarkerDao deleteMarkerDao = ctx.getBean("jpaDeleteMarker",DeleteMarkerDao.class);

        EntityDeleteMarker entityDeleteMarker = new EntityDeleteMarker();
        entityDeleteMarker.setDate(new Timestamp(new Date().getTime()));
        deleteMarkerDao.save(entityDeleteMarker);

        entityOrders.setDeleteMark(entityDeleteMarker.getId());

        return refund(ctx,entityOrders,EntityOrders.STATUS_DELETED);
    }

    public double refundCashback(WebApplicationContext ctx, EntityOrders entityOrders){

        return refund(ctx,entityOrders,EntityOrders.STATUS_REFUNDED);

    }

    private double refund(WebApplicationContext ctx, EntityOrders entityOrders, int status){


        entityOrders.setStatus(status);
        orderDao.save(entityOrders);

        List<EntityBills> billsList = billsDao.selectByOrderIdAndType(entityOrders.getId(),EntityBills.PAY_METHOD_CASHBACK);

        double summCB = 0;

        for (EntityBills bills: billsList){
            EntityTransactions transaction = bills.getTransactionsByTranzaction();
            Long sender = transaction.getSender();
            if(sender != null){
                summCB += transaction.getSumm();
            }
        }
        double refundSumm = entityOrders.getPrepay();

        if(entityOrders.getAccountUsers()!= null) {

            EntityUserShops entityUserShops = userShopDao.findByUserIdAndShop(entityOrders.getAccountUsers().getId(), entityOrders.getShopId());

            refundSumm -= summCB;

            EntityTransactions refundCash = new EntityTransactions();

            refundCash.setPayee(entityOrders.getAccountUsers().getId());
            refundCash.setCurrency(EntityBills.PAY_METHOD_CASH);
            refundCash.setMarketplace(entityOrders.getShopId());
            refundCash.setDete(new Timestamp(new Date().getTime()));

            transactionDao.save(refundCash);

            EntityBills bilsRetCashToClient = new EntityBills();

            bilsRetCashToClient.setOrdersByOrderId(entityOrders);
            bilsRetCashToClient.setType(EntityBills.PAY_METHOD_CASH);
            bilsRetCashToClient.setTransactionsByTranzaction(refundCash);
            orderDao.save(entityOrders);
            billsDao.save(bilsRetCashToClient);

            if (entityUserShops == null) {

                entityUserShops = new EntityUserShops();
                entityUserShops.setShop(entityOrders.getShopId());
                entityUserShops.setCashback(EntityCashback.STANDART_CB);

                return entityOrders.getPrepay();

            }

            EntityTransactions transactions = new EntityTransactions();

            transactions.setCurrency(EntityBills.PAY_METHOD_CASHBACK);

            transactions.setPayee(entityOrders.getAccountUsers().getId());
            transactions.setMarketplace(entityOrders.getShopId());
            transactions.setDete(new Timestamp(new Date().getTime()));
            transactions.setSumm(summCB);

            transactionDao.save(transactions);
            EntityBills bilsRetCbToClient = new EntityBills();
            bilsRetCbToClient.setOrdersByOrderId(entityOrders);
            bilsRetCbToClient.setType(EntityBills.PAY_METHOD_CASHBACK);
            bilsRetCbToClient.setTransactionsByTranzaction(transactions);
            billsDao.save(bilsRetCbToClient);

            entityUserShops.setCashbackActive((float) (entityUserShops.getCashbackActive() + summCB));


            UserService userService = new UserService(ctx);

            double percent = userService.getCasbackPercent(entityUserShops);

            double rfdCBS = (refundSumm / 100) * percent;

            EntityTransactions entityTransactionRefundShopCB = new EntityTransactions();

            entityTransactionRefundShopCB.setCurrency(EntityBills.PAY_METHOD_CASHBACK);
            entityTransactionRefundShopCB.setSender(entityOrders.getAccountUsers().getId());
            entityTransactionRefundShopCB.setMarketplace(entityOrders.getShopId());
            entityTransactionRefundShopCB.setDete(new Timestamp(new Date().getTime()));
            entityTransactionRefundShopCB.setSumm(rfdCBS);

            transactionDao.save(entityTransactionRefundShopCB);

            EntityBills bilsRetCbToShop = new EntityBills();

            bilsRetCbToShop.setOrdersByOrderId(entityOrders);
            bilsRetCbToShop.setType(EntityBills.PAY_METHOD_CASHBACK);
            bilsRetCbToShop.setTransactionsByTranzaction(entityTransactionRefundShopCB);

            billsDao.save(bilsRetCbToShop);

            entityUserShops.setCashbackActive((float) (entityUserShops.getCashbackActive() - rfdCBS));

            userShopDao.save(entityUserShops);
        }
        return refundSumm;
    }

    public void addCashBack(WebApplicationContext ctx, EntityOrders entityOrders){


        EntityUserShops entityUserShops = userShopDao.findByUserIdAndShop(entityOrders.getAccountUsers().getId(),entityOrders.getShopId());

        if(entityUserShops == null){

            entityUserShops = new EntityUserShops();
            entityUserShops.setCashback(EntityCashback.STANDART_CB);
            entityUserShops.setUserId(entityOrders.getAccountUsers().getId());
            entityUserShops.setShop(entityOrders.getShopId());

        }


        float percent = entityUserShops.getCashback();
        EntityUserShops partner = null;
        if(entityUserShops.getRef() != 0){
            partner = userShopDao.findByUserIdAndShop(entityUserShops.getRef(),ShopServletService.SHOP_ID_ICE);
        }

        List<EntityBills> billsList = billsDao.selectByOrderId(entityOrders.getId());

        float summCash = 0;

        for(EntityBills bills: billsList){

            if(bills.getType() != EntityBills.PAY_METHOD_CASHBACK){
                EntityTransactions transaction = bills.getTransactionsByTranzaction();

                if(transaction.getSender() == entityOrders.getAccountUsers().getId()){
                    summCash += transaction.getSumm();
                }
            }

        }

        entityUserShops.setSumm(entityUserShops.getSumm()+summCash);

        float addCash = summCash/100*percent;

        runTransactionChangeCb(entityOrders,entityUserShops, addCash);

        if(partner != null) {

            float addPartner = summCash/100*percent;
            runTransactionChangeCb(entityOrders, partner, addPartner);

        }

    }

    private void runTransactionChangeCb(EntityOrders entityOrders, EntityUserShops entityUserShops, float addCash){



        EntityTransactions transactions = new EntityTransactions();

        transactions.setCurrency(EntityBills.PAY_METHOD_CASHBACK);
        transactions.setPayee(entityUserShops.getUserId());
        transactions.setMarketplace(entityOrders.getShopId());
        transactions.setDete(new Timestamp(new Date().getTime()));
        transactions.setSumm((double) addCash);
        transactionDao.save(transactions);

        EntityBills bilsCB = new EntityBills();

        bilsCB.setTransactionsByTranzaction(transactions);
        bilsCB.setType(EntityBills.PAY_METHOD_CASHBACK);
        bilsCB.setOrdersByOrderId(entityOrders);

        billsDao.save(bilsCB);

        entityUserShops.setCashbackActive(entityUserShops.getCashbackActive()+addCash);

        if(entityOrders.getAccountUsers().getId() == entityUserShops.getUserId()) {
            entityUserShops.setMyCb(entityUserShops.getMyCb() + addCash);
        }else{
            entityUserShops.setFrndCb(entityUserShops.getFrndCb()+addCash);
        }

        userShopDao.save(entityUserShops);

    }

    public List<EntityArticle> selectPriceFromJSONArrayIds(long shopId, JSONArray functionIds){
        List<EntityArticle> articleList = new ArrayList<>();
        for(int i = 0; i<functionIds.length(); i++){
            EntityArticle price = articleDao.selectById(functionIds.getLong(i));
            articleList.add(price);
        }
        return articleList;
    }

    public float summOrder(List<EntityArticle> articles){
        float summ = 0;

        for(EntityArticle article: articles){
            summ+= article.getPrice();
        }
        return summ;
    }

    public  int  lengthOrder(List<EntityArticle> entityArticles){
        int length =0;

        for(EntityArticle article: entityArticles){
            length+= article.getLength();
        }
        return length;
    }

    public JSONObject addOrderShop(JSONObject order){
        long idOrder = 0;

        JSONArray services = order.getJSONArray("articles");
        long client = 0;
        long orderId = 0;
        long employeeId = 0;
        int length=0;
        double summ = 0.0;
        JSONObject address = null;

        GregorianCalendar plannedTime = new CalendarRussia();

        if (order.has("address")) {

            address = order.getJSONObject("address");
        }

        if(order.has("client")){

            client = order.getLong("client");

        }

        if(order.has("order_id")){
            orderId = order.getLong("order_id");
        }

        employeeId = order.getLong("employee_id");

        if(order.has("planned_time")) {

            plannedTime.setTimeInMillis(order.getLong("planned_time"));

        }
        if(order.has("length")) {
            length = order.getInt("length");
        }
        if(length<0){

            errorMessage = "length не может быть отрицательным";
            return null ;

        }

        summ = order.getFloat("summ");

        if(summ<0){
            errorMessage = "summ не может быть отрицательным";

            return null ;
        }

        EmployeeManager employeeManager = new EmployeeManager(ctx,null);

        EmployeeDao employeeDao = ctx.getBean("jpaEmployee",EmployeeDao.class);

        EntityEmployee entityEmployee = employeeDao.selectById(employeeId);


        //todo сервисы могут отсутствовать Карл!!!!!! Добавим автосоздание отгулов???
        if(!employeeManager.validServiceEmployee(services,entityEmployee) && services.length() != 0){

            errorMessage = "Не найдены сервисы";
            return null;

        }

        List<EntityTaskList> taskLists = new ArrayList<>();

        int lengthAll = 0;


        Employee employeeModel = new Employee(entityEmployee);

        employeeModel.setEntityPrices(selectPriceFromJSONArrayIds(entityEmployee.getShopId(),services));


        lengthAll += length;

        EntityTaskList entityTaskList = new EntityTaskList();
        entityTaskList.setEmployee(employeeModel.getId());
        entityTaskList.setLength(length);

        JSONArray check = fixServices(employeeModel.getEntityPrices());
        JSONObject orderJ = new JSONObject();
        entityTaskList.setServices(check.toString());

        entityTaskList.setTime(new Timestamp(plannedTime.getTimeInMillis()));
        taskLists.add(entityTaskList);


        int addIntervalMinute = length*5;
        plannedTime.add(Calendar.MINUTE, addIntervalMinute);
        EntityAccountUsers accountUsers = null;
        JSONObject clientJ = null;

        if(client>0) {

            AccountUserDao accountUserDao = ctx.getBean("jpaAccountUser", AccountUserDao.class);
            accountUsers = accountUserDao.selectByMobile(client);
            if(accountUsers == null){
                ClientService clientService = new ClientService(ctx);
                accountUsers = clientService.regNewClient(client,entityEmployee.getShopId());
            }
            clientJ = new JSONObject();
            clientJ.put("id",accountUsers.getId());
            clientJ.put("phone",accountUsers.getLogin());

        }

        EntityOrders entityOrders = new EntityOrders();

        if(orderId != 0) {

            entityOrders.setId(orderId);

        }

        if (address != null) {

            entityOrders.setAddress(address.toString());
        }

        entityOrders.setAccountUsers(accountUsers);
        entityOrders.setStatus(EntityOrders.STATUS_NEW);
        entityOrders.setFavorite(false);
        entityOrders.setLastModification(new Timestamp(new Date().getTime()));

        entityOrders.setSumm(summ);
        entityOrders.setLength(lengthAll);
        entityOrders.setShopId(entityEmployee.getShopId());
        entityOrders.setPrepay(0D);

        OrderDao orderDao = ctx.getBean("jpaOrder", OrderDao.class);

        orderDao.save(entityOrders);
        orderJ.put("planned_time",plannedTime.getTime().getTime());
        orderJ.put("summ",summ);
        orderJ.put("length",length);
        orderJ.put("check",check);
        orderJ.put("client",clientJ);
        orderJ.put("address",address);
        orderJ.put("status",entityOrders.getStatus());
        orderJ.put("id",entityOrders.getId());

        JSONObject newOrder = new JSONObject();
        newOrder.put("newOrder",orderJ);

        TaskListDao taskListDao = ctx.getBean("jpaTaskList",TaskListDao.class);
        taskListDao.deleteByOrder(entityOrders);
        for (EntityTaskList taskList: taskLists){

            taskList.setOrders(entityOrders);
            taskListDao.save(taskList);
        }

        return newOrder;

    }

    public void deleteAllOrder(){
        BillsDao billsDao = ctx.getBean("jpaBills",BillsDao.class);
        CommentDao commentDao  = ctx.getBean("jpaComment",CommentDao.class);
        TransactionDao transactionDao = ctx.getBean("jpaTransactions",TransactionDao.class);

        List<EntityOrders> ordersList = orderDao.findAll();
        List<EntityBills> allBills = new ArrayList<>();
        List<EntityComment> allComm = new ArrayList<>();
        for(EntityOrders entityOrders: ordersList) {

            List<EntityBills> bills = billsDao.selectByOrderId(entityOrders.getId());

            List<EntityComment> comments = commentDao.findByOrderId(entityOrders.getId());
            allComm.addAll(comments);
            allBills.addAll(bills);

        }
        billsDao.deleteAll(allBills);
        transactionDao.deleteAll();
        commentDao.deleteAll(allComm);
        orderDao.deleteAll();
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
