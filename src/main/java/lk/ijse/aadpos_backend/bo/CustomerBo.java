package lk.ijse.aadpos_backend.bo;

import lk.ijse.aadpos_backend.dto.CustomerDto;

import java.sql.SQLException;

public interface CustomerBo {
    boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(int id) throws SQLException, ClassNotFoundException;
}
