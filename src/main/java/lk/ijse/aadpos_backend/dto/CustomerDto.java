package lk.ijse.aadpos_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {
    Integer cusId;
    String cusName;
    String cusAddress;
    int Salary;
}
