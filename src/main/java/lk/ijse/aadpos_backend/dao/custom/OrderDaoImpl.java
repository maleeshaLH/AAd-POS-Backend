package lk.ijse.aadpos_backend.dao.custom;

import lk.ijse.aadpos_backend.dao.OrderDao;
import lk.ijse.aadpos_backend.dto.ItemDto;
import lk.ijse.aadpos_backend.dto.OrderDto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private DataSource dataSource;
    public OrderDaoImpl(DataSource pool) {
        this.dataSource = pool;
    }

    @Override
    public boolean checkCustomerExists(String customerId) throws SQLException {
        String customerCheckSQL = "SELECT 1 FROM customer WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement checkCustomerStmt = connection.prepareStatement(customerCheckSQL)) {
            checkCustomerStmt.setString(1, customerId);
            var rs = checkCustomerStmt.executeQuery();
            return rs.next();
        }
    }

    @Override
    public int getItemQuantity(Integer itemId) throws SQLException {
        String itemQtyCheckSQL = "SELECT qty FROM items WHERE code = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement itemQtyCheckStmt = connection.prepareStatement(itemQtyCheckSQL)) {
            itemQtyCheckStmt.setString(1, String.valueOf(itemId));
            var rs = itemQtyCheckStmt.executeQuery();
            return rs.next() ? rs.getInt(1) : -1;
        }
    }

    @Override
    public void saveOrder(OrderDto order) throws SQLException {
        String insertOrderSQL = "INSERT INTO orders (order_id, order_date, customer_id, total, discount, subtotal, cash, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement orderStmt = connection.prepareStatement(insertOrderSQL)) {
            orderStmt.setString(1, order.getOrderId());
            orderStmt.setDate(2, java.sql.Date.valueOf(order.getOrderDate()));
            orderStmt.setString(3, order.getCustomerId());
            orderStmt.setDouble(4, order.getTotal());
            orderStmt.setDouble(5, order.getDiscount());
            orderStmt.setDouble(6, order.getSubTotal());
            orderStmt.setDouble(7, order.getCash());
            orderStmt.setDouble(8, order.getBalance());
            orderStmt.executeUpdate();
        }
    }

    @Override
    public void saveOrderItem(String orderId, ItemDto item) throws SQLException {
        String insertOrderItemSQL = "INSERT INTO order_items (order_id, item_code, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement orderItemStmt = connection.prepareStatement(insertOrderItemSQL)) {
            orderItemStmt.setString(1, orderId);
            orderItemStmt.setString(2, String.valueOf(item.getItemId()));
            orderItemStmt.setInt(3, item.getItemQty());
            orderItemStmt.setDouble(4,item.getItemPrice());
            orderItemStmt.executeUpdate();
        }
    }

    @Override
    public void updateItemQuantity(ItemDto item) throws SQLException {
        String updateItemQtySQL = "UPDATE items SET qty = qty - ? WHERE code = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement updateItemStmt = connection.prepareStatement(updateItemQtySQL)) {
            updateItemStmt.setInt(1, item.getItemQty());
            updateItemStmt.setString(2, String.valueOf(item.getItemId()));
            updateItemStmt.executeUpdate();
        }
    }

    @Override
    public List<OrderDto> getAllOrders() throws SQLException {
        List<OrderDto> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrderDto order = new OrderDto();
                order.setOrderId(rs.getString("order_id"));
                order.setOrderDate(rs.getString("order_date"));
                order.setCustomerId(rs.getString("customer_id"));
                order.setTotal(rs.getDouble("total"));
                order.setDiscount(rs.getDouble("discount"));
                order.setSubTotal(rs.getDouble("subtotal"));
                order.setCash(rs.getDouble("cash"));
                order.setBalance(rs.getDouble("balance"));

                // Optionally, load items related to the order
                order.setItems(getItemsForOrder(order.getOrderId(), connection));

                orders.add(order);
            }
        }

        return orders;
    }

    private List<ItemDto> getItemsForOrder(String orderId, Connection connection) throws SQLException {
        List<ItemDto> items = new ArrayList<>();
        String query = "SELECT oi.item_code, i.description, oi.price, oi.quantity FROM order_items oi " +
                "JOIN items i ON oi.item_code = i.code WHERE oi.order_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemDto item = new ItemDto();
                    item.setItemId(rs.getInt("itemId"));
                    item.setItemName(rs.getString("description"));
                    item.setItemQty(rs.getInt("price"));
                    item.setItemPrice(rs.getInt("quantity"));

                    items.add(item);
                }
            }

        }

        return items;
    }
}
