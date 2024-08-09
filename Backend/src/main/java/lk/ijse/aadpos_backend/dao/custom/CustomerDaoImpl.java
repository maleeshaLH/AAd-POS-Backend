package lk.ijse.aadpos_backend.dao.custom;

import lk.ijse.aadpos_backend.dao.CustomerDao;
import lk.ijse.aadpos_backend.db.DbConnection;
import lk.ijse.aadpos_backend.dto.CustomerDto;
import lk.ijse.aadpos_backend.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    private Connection connection;
    PreparedStatement pstm;

    @Override
    public String  save(CustomerDto customer, Connection connection) throws SQLException, ClassNotFoundException {
        //this.connection = DbConnection.getInstance().getConnection();

       PreparedStatement ps = connection.prepareStatement("INSERT INTO customer (cusId,cusName,cusAddress,Salary) VALUES(?,?,?,?)");
        ps.setString(1,customer.getCusId());
        ps.setString(2,customer.getCusName());
        ps.setString(3,customer.getCusAddress());
        ps.setInt(4,customer.getSalary());
       if (ps.executeUpdate()>0){
           return "success";
       }else {
           return "fail";
       }
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        connection =DbConnection.getInstance().getConnection();

        pstm = connection.prepareStatement("UPDATE customer SET cusName=?,cusAddress=?,Salary=? WHERE cusId=?");
        pstm.setString(1,customer.getCusName());
        pstm.setString(2,customer.getCusAddress());
        pstm.setInt(3,customer.getSalary());
        pstm.setString(4,customer.getCusId());
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean delete(int id) throws SQLException, ClassNotFoundException {
        connection =DbConnection.getInstance().getConnection();
        pstm = connection.prepareStatement("DELETE FROM customer WHERE cusId=?",id);
        pstm.setInt(1,id);

        return pstm.executeUpdate()>0;
    }

    @Override
    public List<CustomerDto> getAllCustomers(Connection connection) {
        List<CustomerDto> customers = new ArrayList<>();
        String query = "SELECT * FROM customer";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CustomerDto customer = new CustomerDto();
                customer.setCusId(rs.getString("cusId"));
                customer.setCusName(rs.getString("cusName"));
                customer.setCusAddress(rs.getString("cusAddress"));
                customer.setSalary(rs.getInt("Salary"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }
}
