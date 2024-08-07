package lk.ijse.aadpos_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDto {
    String itemId;
    String itemName;
    Integer itemQty;
    Integer itemPrice;
}
