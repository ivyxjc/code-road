package xyz.ivyxjc.coderoad.spring.transaction.model;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Trans extends BaseBean {

    private String guid;

    private String transId;

    private BigDecimal transAmount;

    private LocalDateTime transDate;

}
