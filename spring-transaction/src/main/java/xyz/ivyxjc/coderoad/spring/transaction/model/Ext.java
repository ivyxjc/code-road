package xyz.ivyxjc.coderoad.spring.transaction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ext extends BaseBean {

    private String guid;

    private String extId;

    private BigDecimal extAmount;

    private LocalDateTime extDate;
}
