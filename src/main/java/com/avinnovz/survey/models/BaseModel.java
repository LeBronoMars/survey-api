package com.avinnovz.survey.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Base implementation of model classes
 *
 * Created by rsbulanon on 5/27/17.
 */
@Data
@MappedSuperclass
public abstract class BaseModel implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)", length = 36)
    protected String id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(name = "updated", nullable = false)
    @JsonProperty("updated_at")
    private Date updatedAt;

    @Column
    private Boolean active;

    @Column
    @JsonProperty("is_synced")
    @ApiModelProperty(example = "true")
    private boolean isSynced;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = new Date();
        active = true;
        isSynced = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
