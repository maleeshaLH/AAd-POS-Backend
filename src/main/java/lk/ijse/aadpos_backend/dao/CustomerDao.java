package lk.ijse.aadpos_backend.dao;

import lk.ijse.aadpos_backend.dto.CustomerDto;
import lk.ijse.aadpos_backend.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CustomerDao {
    String save(CustomerDto customer, Connection connection) throws SQLException, ClassNotFoundException;

    boolean update(Customer customer) throws SQLException, ClassNotFoundException;

    boolean delete(int id) throws SQLException, ClassNotFoundException;

    List<CustomerDto> getAllCustomers(Connection connection);
}
