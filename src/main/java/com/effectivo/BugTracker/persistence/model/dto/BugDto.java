package com.effectivo.BugTracker.persistence.model.dto;

import java.time.LocalDateTime;

public class BugDto {

    private Long id;

    private String title;

    private String description;

    private Integer status;

    private LocalDateTime fixedAt;

    private LocalDateTime createdAt;

    public BugDto(){

    }

    public BugDto(Long id, String title, String description, Integer status, LocalDateTime fixedAt, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.fixedAt = fixedAt;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getFixedAt() {
        return fixedAt;
    }

    public void setFixedAt(LocalDateTime fixedAt) {
        this.fixedAt = fixedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
