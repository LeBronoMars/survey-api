package com.avinnovz.survey.dto.questionnaire;

import com.avinnovz.survey.dto.department.DepartmentDto;
import com.avinnovz.survey.dto.questions.QuestionDto;
import com.avinnovz.survey.dto.user.AppUserDto;
import com.avinnovz.survey.dto.user.SimplifiedAppUserDto;
import com.avinnovz.survey.models.Question;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Data
public class QuestionnaireDto {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("created_at")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("updated_at")
    private Date updatedAt;

    private Boolean active;

    @ApiModelProperty(example = "Questionnaire 1")
    private String name;

    @ApiModelProperty(example = "Lorem ipsum dolor")
    private String description;

    private DepartmentDto department;

    private Set<QuestionDto> questions;

    @ApiModelProperty(example = "user_id")
    @JsonProperty("created_by")
    private SimplifiedAppUserDto createdBy;

    @ApiModelProperty(example = "user_id")
    @JsonProperty("updated_by")
    private SimplifiedAppUserDto updatedBy;
}
