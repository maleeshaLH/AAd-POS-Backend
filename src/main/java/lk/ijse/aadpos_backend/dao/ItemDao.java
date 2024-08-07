package lk.ijse.aadpos_backend.dao;

import lk.ijse.aadpos_backend.dto.ItemDto;
import lk.ijse.aadpos_backend.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemDao {

    boolean save(Item item) throws SQLException, ClassNotFoundException;

    boolean update(Item item) throws SQLException, ClassNotFoundException;

    boolean delete(int id) throws SQLException, ClassNotFoundException;
}
