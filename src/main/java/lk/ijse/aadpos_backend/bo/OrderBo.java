package lk.ijse.aadpos_backend.bo;

import lk.ijse.aadpos_backend.dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderBo {
    void placeOrder(OrderDto order) throws SQLException;
    List<OrderDto> getAllOrders() throws SQLException;

}
