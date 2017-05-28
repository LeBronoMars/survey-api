package com.avinnovz.survey.dto.choice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Data
public class ChoiceDto {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("created_at")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("updated_at")
    private Date updatedAt;

    private Boolean active;

    @ApiModelProperty(example = "Choice 1")
    private String name;
}
