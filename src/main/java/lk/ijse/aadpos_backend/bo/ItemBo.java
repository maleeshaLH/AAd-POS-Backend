package lk.ijse.aadpos_backend.bo;

import lk.ijse.aadpos_backend.dto.ItemDto;

import java.sql.SQLException;

public interface ItemBo {
    boolean saveItem(ItemDto item) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDto item) throws SQLException, ClassNotFoundException;

    boolean deleteItem(int i) throws SQLException, ClassNotFoundException;
}
