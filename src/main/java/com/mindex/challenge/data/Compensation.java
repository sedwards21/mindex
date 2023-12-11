package com.mindex.challenge.data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Compensation {
    private String employeeId;
    private BigDecimal salary;
    private Date effectiveDate;
}
