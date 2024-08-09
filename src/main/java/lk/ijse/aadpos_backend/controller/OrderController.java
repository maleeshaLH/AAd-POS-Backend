package lk.ijse.aadpos_backend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.aadpos_backend.bo.OrderBo;
import lk.ijse.aadpos_backend.bo.custom.OrderBoImpl;
import lk.ijse.aadpos_backend.dao.OrderDao;
import lk.ijse.aadpos_backend.dao.custom.OrderDaoImpl;
import lk.ijse.aadpos_backend.dto.OrderDto;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class OrderController extends HttpServlet {
    static Logger logger = (Logger) LoggerFactory.getLogger(OrderController.class);
    private OrderBo orderBO;
    @Override
    public void init() throws ServletException {
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/customer");
            OrderDao orderDAO = new OrderDaoImpl(pool);
            this.orderBO = new OrderBoImpl(orderDAO);
        } catch (NamingException e) {
            throw new ServletException("DB connection not initialized", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try (var reader = req.getReader()) {
            Jsonb jsonb = JsonbBuilder.create();
            OrderDto order = jsonb.fromJson(reader, OrderDto.class);

            if (order == null || order.getItems() == null || order.getItems().isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order data");
                return;
            }

            orderBO.placeOrder(order);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Order placed successfully");

        } catch (SQLException e) {
            //logger.error("Error while placing order", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to place order: " + e.getMessage());
        } catch (Exception e) {
            //logger.error("Failed to parse request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to parse request: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            List<OrderDto> orders = orderBO.getAllOrders(); // Implement this method in your BO layer
            Jsonb jsonb = JsonbBuilder.create();
            String json = jsonb.toJson(orders);
            resp.getWriter().write(json);
        } catch (SQLException e) {
            //logger.error("Error while fetching orders", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to fetch orders: " + e.getMessage());
        }
    }
}
