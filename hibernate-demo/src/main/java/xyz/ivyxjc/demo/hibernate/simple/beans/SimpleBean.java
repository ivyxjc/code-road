package xyz.ivyxjc.demo.hibernate.simple.beans;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

@Data
@Table(name = "SIMPLE_BEAN")
public class SimpleBean {

    @Column(name = "GUID")
    private String guid;

    @Column(name = "UNIQUE_ID")
    private String uniqueId;

    @Column(name = "TRAN_COUNT")
    private Long tranCount;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @JoinColumn(name = "UNIQUE_ID", referencedColumnName = "UNIQUE_ID")
    @JoinColumn(name = "GUID", referencedColumnName = "GUID")
    private SimpleBeanChild childPo;

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
