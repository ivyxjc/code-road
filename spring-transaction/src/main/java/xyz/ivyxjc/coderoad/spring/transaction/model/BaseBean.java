package xyz.ivyxjc.coderoad.spring.transaction.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
class BaseBean {

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;
}
