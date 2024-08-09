package lk.ijse.aadpos_backend.dao.custom;

import lk.ijse.aadpos_backend.dao.ItemDao;
import lk.ijse.aadpos_backend.db.DbConnection;
import lk.ijse.aadpos_backend.entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemDaoImpl implements ItemDao {
private Connection connection;
PreparedStatement pstm;
    public  static  String SAVE ="INSERT INTO item (itemId,ItemName,ItemQty,ItemPrice) VALUES(?,?,?,?)";
    public static String UPDATE = "UPDATE item SET itemName=?,ItemQty=?,ItemPrice=? WHERE itemId=?";
    public static String DELETE = "DELETE FROM item WHERE itemId=?";
    @Override
    public boolean save(Item item) throws SQLException, ClassNotFoundException {
        connection = DbConnection.getInstance().getConnection();
        pstm = connection.prepareStatement(SAVE);
        pstm.setInt(1, item.getItemId());
        pstm.setString(2, item.getItemName());
        pstm.setInt(3,item.getItemQty());
        pstm.setInt(4,item.getItemPrice());
        return pstm.executeUpdate() >0;
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        connection =DbConnection.getInstance().getConnection();
        pstm = connection.prepareStatement(UPDATE);
        pstm.setString(1, item.getItemName());
        pstm.setInt(2, item.getItemQty());
        pstm.setInt(3, item.getItemPrice());
        pstm.setInt(4, item.getItemId());
        return pstm.executeUpdate() >0;
    }

    @Override
    public boolean delete(int id) throws SQLException, ClassNotFoundException {

        connection =DbConnection.getInstance().getConnection();
        pstm = connection.prepareStatement(DELETE);
        pstm.setInt(1, id);
        return pstm.executeUpdate() >0;
    }
}
