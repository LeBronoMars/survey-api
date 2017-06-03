package com.avinnovz.survey.dto.department;

import com.avinnovz.survey.dto.user.SimplifiedAppUserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Data
public class DepartmentDto {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("created_at")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("updated_at")
    private Date updatedAt;

    private Boolean active;

    private String name;

    private String description;

    private List<SimplifiedAppUserDto> members;
}
