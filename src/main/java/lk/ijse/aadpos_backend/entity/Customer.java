package lk.ijse.aadpos_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    Integer cusId;
    String cusName;
    String cusAddress;
    int Salary;
}
