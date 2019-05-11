package xyz.ivyxjc.demo.hibernate.simple.beans;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Table;

//@Data
@Table(name = "SIMPLE_BEAN")
public class SimpleBeanChild {

    @Column(name = "GUID")
    private String guid;

    @Column(name = "UNIQUE_ID")
    private String uniqueId;

    @Column(name = "VALUE_DATE")
    private LocalDate valueDate;

    @Column(name = "VALUE_COUNT")
    private BigDecimal valueCount;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    @Column(name = "CREATED_FROM", updatable = false)
    private String createdFrom;

    @Column(name = "UPDATED_AT", insertable = false)
    private LocalDateTime updatedAt;

    @Column(name = "UPDATED_BY", insertable = false)
    private String updatedBy;

    @Column(name = "UPDATED_FROM", insertable = false)
    private String updatedFrom;
}
