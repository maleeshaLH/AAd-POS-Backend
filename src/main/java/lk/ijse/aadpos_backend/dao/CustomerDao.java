package lk.ijse.aadpos_backend.dao;

import lk.ijse.aadpos_backend.entity.Customer;

import java.sql.SQLException;

public interface CustomerDao {
    boolean save(Customer customer) throws SQLException, ClassNotFoundException;

    boolean update(Customer customer) throws SQLException, ClassNotFoundException;

    boolean delete(int id) throws SQLException, ClassNotFoundException;
}
