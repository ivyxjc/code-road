package xyz.ivyxjc.coderoad.spring.transaction.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Ext extends BaseBean {

    private String guid;

    private String extId;

    private BigDecimal extAmount;

    private LocalDateTime extDate;
}
