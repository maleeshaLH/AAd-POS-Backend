package lk.ijse.aadpos_backend.bo.custom;

import lk.ijse.aadpos_backend.bo.CustomerBo;
import lk.ijse.aadpos_backend.dao.CustomerDao;
import lk.ijse.aadpos_backend.dao.custom.CustomerDaoImpl;
import lk.ijse.aadpos_backend.dto.CustomerDto;
import lk.ijse.aadpos_backend.entity.Customer;

import java.sql.SQLException;

public class CustomerBoImpl implements CustomerBo {
    CustomerDao customerDao = new CustomerDaoImpl();
    @Override
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDao.save(
                new Customer(
                        customerDto.getCusId(),
                        customerDto.getCusName(),
                        customerDto.getCusAddress(),
                        customerDto.getSalary()
                )
        );
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
}
