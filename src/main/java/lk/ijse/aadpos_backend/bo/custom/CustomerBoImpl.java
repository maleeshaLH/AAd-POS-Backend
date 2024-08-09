package lk.ijse.aadpos_backend.bo.custom;

import lk.ijse.aadpos_backend.bo.CustomerBo;
import lk.ijse.aadpos_backend.dao.CustomerDao;
import lk.ijse.aadpos_backend.dao.custom.CustomerDaoImpl;
import lk.ijse.aadpos_backend.dto.CustomerDto;
import lk.ijse.aadpos_backend.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {
    CustomerDao customerDao = new CustomerDaoImpl();
    @Override
    public String saveCustomer(CustomerDto customerDto, Connection connection) throws SQLException, ClassNotFoundException {
        return customerDao.save(customerDto,connection);

    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDao.update(
                new Customer(
                        customerDto.getCusId(),
                        customerDto.getCusName(),
                        customerDto.getCusAddress(),
                        customerDto.getSalary()
                )
        );
    }

    @Override
    public boolean deleteCustomer(int id) throws SQLException, ClassNotFoundException {
        return customerDao.delete(id);
    }

    @Override
    public List<CustomerDto> getAllCustomers(Connection connection) {
        return List.of();
    }
}
