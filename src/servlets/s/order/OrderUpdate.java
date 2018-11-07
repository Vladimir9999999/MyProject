package servlets.s.order;

import Models.OrderEntity;
import dao.OrderEntityDao;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "servlets.s.order.OrderUpdate",
            urlPatterns = "/update.order")


public class OrderUpdate extends HttpServlet {
 String c = "update";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");


        JSONObject bodyJ = new JSONObject();
        JSONObject statusJ = new JSONObject();
        JSONObject respJ = new JSONObject();
        statusJ.put("case",c);
        OrderEntity orderEntity = new OrderEntity();

        if (request.getParameter("prepay") != null) {
            String prepay = request.getParameter("prepay");

            orderEntity.setPrepay(Double.parseDouble(prepay));
        }

        try {

            long orderId = Long.parseLong(request.getParameter("id_order"));
            orderEntity.setId(orderId);//todo проверка полномочий изменяющего

        }catch (Exception e){

           statusJ.put("error","отсутствуют необходиые параметры");

        }
        try {

            String services = request.getParameter("services");

            if(services!=null){

                JSONArray servicesJA = new JSONArray();
                orderEntity.setServices(servicesJA.toString());

            }
            if(request.getParameter("emploee") != null) {

                int emploee = Integer.parseInt(request.getParameter("emploee"));
                orderEntity.setEmployeeId(emploee);

            }

            String plannedTime = request.getParameter("planned_time");

            if(plannedTime != null){

                orderEntity.setPlannedtime(Timestamp.valueOf(plannedTime));

            }
            if(request.getParameter("length") != null) {

                int length = Integer.parseInt(request.getParameter("length"));
                orderEntity.setLendth(length);

            }
            if(request.getParameter("summ") != null) {

                float summ = Float.parseFloat(request.getParameter("summ"));
                orderEntity.setSumm(Double.valueOf(summ));

            }
            if(request.getParameter("status")!=null){

                orderEntity.setStatus(request.getParameter("status"));

            }

            OrderEntityDao dao = new OrderEntityDao();

            if(dao.update(orderEntity)){
                //bodyJ.put("\")
                statusJ.put("error","ok");

            }else {
                statusJ.put("error","Не удалось записать изменения ордера");
            }
        }catch (Exception e){
            statusJ.put("error","отсутствуют обязательные параметры");
        }

        respJ.put("status",statusJ);
        respJ.put("body",bodyJ);

        response.getWriter().print(respJ);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
