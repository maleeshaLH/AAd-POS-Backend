package lk.ijse.aadpos_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    String  cusId;
    String cusName;
    String cusAddress;
    int Salary;
}
