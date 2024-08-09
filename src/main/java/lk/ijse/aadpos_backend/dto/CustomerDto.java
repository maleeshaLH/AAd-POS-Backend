package lk.ijse.aadpos_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {
    private String cusId;
    private String cusName;
    private String cusAddress;
    private int Salary;
}
