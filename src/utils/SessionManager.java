package utils;

import Models.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import servlets.ShopServletService;
import spring.entity.EntitySession;
import spring.interfaces.SessionDao;

import javax.validation.constraints.Null;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

//import static javax.script.ScriptEngine.FILENAME;

public class SessionManager implements Serializable {

    private static String filePath = null;


    private static int deleted = 0;

    private static LinkedList<Session> sessions = new LinkedList<>();
    private static List<EntitySession> deleteSessionList = new ArrayList<>();
    private static boolean autodelete = false;

    public static void runClear(){

        autodelete = true;

    }

    public synchronized static void addSession(Session session, WebApplicationContext ctx) {

        session.setLastUseDate(new Date());
        sessions.add(session);
        EntitySession entitySession = new EntitySession();
        entitySession.setLastVisit(new Timestamp(new Date().getTime()));
        entitySession.setTocken(session.getToken());

        entitySession.setUserAgent(session.getUserAgent());
        entitySession.setUserId(session.getUserId());

        SessionDao sessionDao = ctx.getBean("jpaSession", SessionDao.class);

        if (session.getiTocken() != null) {

            sessionDao.deleteByAppleTocken(session.getiTocken());

            entitySession.setAppleTocken(session.getiTocken());
        }

        if (session.getgTocken() != null) {

            sessionDao.deleteByGoogleTocken(session.getgTocken());

            entitySession.setGoogleTocken(session.getgTocken());
        }

        sessionDao.save(entitySession);

        long timeout = new Date().getTime();
        long mounth = 30 * 24 * 3600 * 1000;

        System.out.println(timeout + "-" + mounth);

        if(autodelete){

            removeOldSession(ctx);
            autodelete = false;

        }

    }


    public static void deleteSession(Session session, WebApplicationContext ctx) {

        sessions.remove(session);

        SessionDao sessionDao = ctx.getBean("jpaSession",SessionDao.class);

        sessionDao.deleteByUserIdAndTocken(session.getUserId(),session.getToken());

        System.out.println(sessions.size());

        deleted++;

    }

    public static void removeOldSession(WebApplicationContext ctx){

        SessionDao sessionDao = ctx.getBean("jpaSession",SessionDao.class);
        List<EntitySession> allSession = sessionDao.findAll();

        class UniqueSession extends EntitySession{
            private UniqueSession(EntitySession s){
                this.setAppleTocken(s.getAppleTocken());
                this.setGoogleTocken(s.getGoogleTocken());
                this.setLastVisit(s.getLastVisit());
                this.setUserAgent(s.getUserAgent());
                this.setUserId(s.getUserId());
                this.setTocken(s.getTocken());
                this.setId(s.getId());

            }

            @Override
            public int hashCode() {
                return Objects.hash(getUserId(),getUserAgent(),getAppleTocken());
            }
            @Override
            public boolean equals(Object o){
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                EntitySession session = (EntitySession) o;
                return getUserId() == session.getUserId() &&
                        Objects.equals(getUserAgent(), session.getUserAgent()) &&

                        Objects.equals(getAppleTocken(), session.getAppleTocken());
            }

        }
        Set<EntitySession> uniqueSessionSet = new HashSet<>();

        List<EntitySession> oldSession = new ArrayList<>();

        for (EntitySession session: allSession){


            UniqueSession s = new UniqueSession(session);
            oldSession.add(s);

            uniqueSessionSet.add(s);

        }

        for(EntitySession s: uniqueSessionSet){
            removeOld(s,oldSession);
        }

        if(removeIds.size()>0){

            for (EntitySession s: allSession){
                if(removeIds.contains(s.getId())){
                    deleteSessionList.add(s);
                }
            }

            sessionDao.deleteAll(deleteSessionList);

            deleteSessionList.clear();
        }

        System.out.println(oldSession.size() == uniqueSessionSet.size());

    }
    static  List<Long> removeIds = new ArrayList<>();

    private static void removeOld(EntitySession uniqueS, List<EntitySession> allSession) {

        int index = allSession.indexOf(uniqueS);

        if (index >= 0){
            if(uniqueS.getId() != allSession.get(index).getId()) {

                removeIds.add(allSession.get(index).getId());
                allSession.remove(index);


                removeOld(uniqueS, allSession);

            }else {

                allSession.remove(index);
                removeOld(uniqueS,allSession);
            }
        }

    }
    public static boolean searshSession(Session session, WebApplicationContext ctx) {


        int index = sessions.indexOf(session);
        if (index >= 0) {

            Session s = sessions.get(index);

            session.setUserId(s.getUserId());
            session.setPrivilege(s.getPrivilege());
            session.setLastUseDate(new Date());

            return true;
        } else {

            SessionDao sessionDao = ctx.getBean("jpaSession", SessionDao.class);
            EntitySession entitySession = sessionDao.findByUserIdAndTocken(session.getUserId(), session.getToken());
            if (entitySession == null) {
                return false;
            }

            Session sessionDb = new Session();


            sessionDb.setUserId(entitySession.getUserId());
            sessionDb.setShopId(ShopServletService.SHOP_ID_ICE);
            sessionDb.setToken(entitySession.getTocken());
            sessionDb.setPrivilege(255);
            sessionDb.setLastUseDate(new Date());
            sessionDb.setUserAgent(entitySession.getUserAgent());

            sessions.add(sessionDb);
            return true;

        }
    }

    public static boolean saveList(String dir) {
        JSONArray sessionsJson = new JSONArray();

        for (Session s : sessions) {
            JSONObject sessionJson = new JSONObject(s);
            sessionsJson.put(sessionJson);
        }

        filePath = dir + "/sessions.js";
        File shopPage = new File(filePath);


        System.out.println(filePath);
        try (FileWriter writer = new FileWriter(shopPage)) {

            writer.write(sessionsJson.toString());
            writer.flush();

        } catch (IOException e) {

            e.printStackTrace();

        }

        System.out.println(sessionsJson);

        return false;
    }

    public static boolean load(String dir) {

        try {
            filePath = dir + "/sessions.json";
            System.out.println(filePath);
            JSONArray sessions = new JSONArray(new FileReader(filePath));
            System.out.println(sessions);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }
}
