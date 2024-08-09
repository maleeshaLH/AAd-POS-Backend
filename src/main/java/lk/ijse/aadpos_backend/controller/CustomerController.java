package lk.ijse.aadpos_backend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.aadpos_backend.Util.Util;
import lk.ijse.aadpos_backend.bo.CustomerBo;
import lk.ijse.aadpos_backend.bo.custom.CustomerBoImpl;
import lk.ijse.aadpos_backend.dao.CustomerDao;
import lk.ijse.aadpos_backend.dao.custom.CustomerDaoImpl;
import lk.ijse.aadpos_backend.db.DbConnection;
import lk.ijse.aadpos_backend.dto.CustomerDto;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/customer")
public class CustomerController extends HttpServlet {

    CustomerBo customerBo = new CustomerBoImpl();
    static Logger logger = Logger.getLogger(CustomerController.class.getName());
    Connection connection;
    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("init");

        try {
            try {
                var ctx = new InitialContext();
                DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/customer");
                this.connection = pool.getConnection();
                logger.info("connection established");
            } catch (SQLException |NamingException e) {
                logger.info("Connnection failed");
                throw new ServletException(e);

            }

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getContentType() ==null || !req.getContentType().contains("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.info("Invalid request");
        }

        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            var customerBOIMPL = new CustomerBoImpl();
            CustomerDto customer = jsonb.fromJson(req.getReader(), CustomerDto.class);
            logger.info("Invoke idGenerate()");
            customer.setCusId(Util.idGenerate());
            //Save data in the DB
            writer.write(customerBOIMPL.saveCustomer(customer,connection));
            logger.info("Customer saved successfully");
            resp.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getContentType() ==null || !req.getContentType().contains("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDto  customerDto = jsonb.fromJson(req.getReader(), CustomerDto.class);

            if(customerBo.updateCustomer(customerDto)){
                resp.setStatus(HttpServletResponse.SC_CREATED);
                writer.write("Customer updated successfully");
            }else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.write("Customer update failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("cusId"));
            if (customerBo.deleteCustomer(id)){
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Customer deleted successfully");
            }else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Customer delete failed");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDao customerDao = new CustomerDaoImpl();
            List<CustomerDto> customers = customerDao.getAllCustomers(connection); // Add this method to retrieve all customers
            String json = jsonb.toJson(customers);
            writer.write(json);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
