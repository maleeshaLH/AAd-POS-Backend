package lk.ijse.aadpos_backend.bo.custom;

import lk.ijse.aadpos_backend.bo.OrderBo;
import lk.ijse.aadpos_backend.dao.OrderDao;
import lk.ijse.aadpos_backend.dto.ItemDto;
import lk.ijse.aadpos_backend.dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public class OrderBoImpl implements OrderBo {
    private OrderDao orderDao;

    public OrderBoImpl(OrderDao orderDAO) {
        this.orderDao = orderDAO;
    }

    @Override
    public void placeOrder(OrderDto order) throws SQLException {
        if (!orderDao.checkCustomerExists(order.getCustomerId())) {
            throw new SQLException("Customer ID not found");
        }

        for (ItemDto item : order.getItems()) {
            int availableQty = orderDao.getItemQuantity(item.getItemId());
//            if (availableQty < item.getQty()) {
//                throw new SQLException("Insufficient stock for item " + item.getCode());
//            }
            int itemQty = item.getItemQty();
            if (availableQty < itemQty) {
                throw new SQLException("Insufficient stock for item " + item.getItemId());
            }
        }

        orderDao.saveOrder(order);

        for (ItemDto item : order.getItems()) {
            orderDao.saveOrderItem(order.getOrderId(), item);
            orderDao.updateItemQuantity(item);
        }
    }

    @Override
    public List<OrderDto> getAllOrders() throws SQLException {
        return orderDao.getAllOrders();
    }
}
