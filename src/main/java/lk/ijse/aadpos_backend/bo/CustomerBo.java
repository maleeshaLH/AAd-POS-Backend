package lk.ijse.aadpos_backend.bo;

import lk.ijse.aadpos_backend.dto.CustomerDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CustomerBo {
    String saveCustomer(CustomerDto customer, Connection connection)throws Exception;

    boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(int id) throws SQLException, ClassNotFoundException;

    List<CustomerDto> getAllCustomers(Connection connection);
}
