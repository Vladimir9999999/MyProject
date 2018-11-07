package interfaces;



import Models.OrderEntity;

import java.util.List;

public interface OrderInterface {
    boolean insert(OrderEntity order);
    boolean delete(long idOrder);
    List<OrderEntity> selectByShop(int idShop, int limit, int offset);
    List<OrderEntity> selectByUser(int idUser);
    boolean update(OrderEntity order);
}
