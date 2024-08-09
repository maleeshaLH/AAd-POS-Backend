package lk.ijse.aadpos_backend.dao;

import lk.ijse.aadpos_backend.dto.ItemDto;
import lk.ijse.aadpos_backend.dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    boolean checkCustomerExists(String customerId) throws SQLException;

    int getItemQuantity(Integer itemId) throws SQLException;

    void saveOrder(OrderDto order) throws SQLException;

    void saveOrderItem(String orderId, ItemDto item) throws SQLException;

    void updateItemQuantity(ItemDto item) throws SQLException;

    List<OrderDto> getAllOrders() throws SQLException;


}
