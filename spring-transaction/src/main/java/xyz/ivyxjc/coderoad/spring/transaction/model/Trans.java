package xyz.ivyxjc.coderoad.spring.transaction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Trans extends BaseBean {

    private String guid;

    private String transId;

    private BigDecimal transAmount;

    private LocalDateTime transDate;

}
