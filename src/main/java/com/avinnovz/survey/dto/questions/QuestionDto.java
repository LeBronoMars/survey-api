package com.avinnovz.survey.dto.questions;

import com.avinnovz.survey.dto.choice.ChoiceDto;
import com.avinnovz.survey.dto.choice.CreateChoiceDto;
import com.avinnovz.survey.dto.user.AppUserDto;
import com.avinnovz.survey.enums.QuestionType;
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
public class QuestionDto {

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
    private QuestionType questionType;

    private Set<ChoiceDto> choices;

    @ApiModelProperty(example = "user_id")
    @JsonProperty("created_by")
    private AppUserDto createdBy;

    @ApiModelProperty(example = "user_id")
    @JsonProperty("updated_by")
    private AppUserDto updatedBy;
}
