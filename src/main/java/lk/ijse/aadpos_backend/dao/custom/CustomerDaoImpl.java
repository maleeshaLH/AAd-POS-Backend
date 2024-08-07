package lk.ijse.aadpos_backend.dao.custom;

import lk.ijse.aadpos_backend.dao.CustomerDao;
import lk.ijse.aadpos_backend.db.DbConnection;
import lk.ijse.aadpos_backend.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDaoImpl implements CustomerDao {
    private Connection connection;
    PreparedStatement pstm;

    @Override
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        connection = DbConnection.getInstance().getConnection();

        pstm =connection.prepareStatement("INSERT INTO customer (cusId,cusName,cusAddress,Salary) VALUES(?,?,?,?)");
        pstm.setInt(1,customer.getCusId());
        pstm.setString(2,customer.getCusName());
        pstm.setString(3,customer.getCusAddress());
        pstm.setInt(4,customer.getSalary());
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        connection =DbConnection.getInstance().getConnection();

        pstm = connection.prepareStatement("UPDATE customer SET cusName=?,cusAddress=?,Salary=? WHERE cusId=?");
        pstm.setString(1,customer.getCusName());
        pstm.setString(2,customer.getCusAddress());
        pstm.setInt(3,customer.getSalary());
        pstm.setInt(4,customer.getCusId());
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean delete(int id) throws SQLException, ClassNotFoundException {
        connection =DbConnection.getInstance().getConnection();
        pstm = connection.prepareStatement("DELETE FROM customer WHERE cusId=?",id);
        pstm.setInt(1,id);

        return pstm.executeUpdate()>0;
    }
}
