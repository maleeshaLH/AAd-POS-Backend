package lk.ijse.aadpos_backend.bo.custom;

import lk.ijse.aadpos_backend.bo.ItemBo;
import lk.ijse.aadpos_backend.dao.ItemDao;
import lk.ijse.aadpos_backend.dao.custom.ItemDaoImpl;
import lk.ijse.aadpos_backend.dto.ItemDto;
import lk.ijse.aadpos_backend.entity.Item;

import java.sql.SQLException;

public class ItemBoImpl implements ItemBo {

    ItemDao itemDao = new ItemDaoImpl();
    @Override
    public boolean saveItem(ItemDto item) throws SQLException, ClassNotFoundException {
        return itemDao.save(new Item(
                item.getItemId(),
                item.getItemName(),
                item.getItemQty(),
                item.getItemPrice()
        ));

    }

    @Override
    public boolean updateItem(ItemDto item) throws SQLException, ClassNotFoundException {
        return itemDao.update(
                new Item(
                        item.getItemId(),
                        item.getItemName(),
                        item.getItemQty(),
                        item.getItemPrice()
                )
        );
    }

    @Override
    public boolean deleteItem(int id) throws SQLException, ClassNotFoundException {
        return itemDao.delete(id);
    }
}
