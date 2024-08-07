package lk.ijse.aadpos_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    Integer itemId;
    String itemName;
    Integer itemQty;
    Integer itemPrice;
}

