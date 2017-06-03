package com.avinnovz.survey.dto.questions;

import com.avinnovz.survey.dto.user.SimplifiedAppUserDto;
import com.avinnovz.survey.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Data
public class SimplifiedQuestionDto {

    private String id;

    @ApiModelProperty(example = "Questionnaire 1")
    private String name;

    @ApiModelProperty(example = "Lorem ipsum dolor")
    private QuestionType questionType;

    @ApiModelProperty(example = "user_id")
    @JsonProperty("created_by")
    private SimplifiedAppUserDto createdBy;

    @ApiModelProperty(example = "user_id")
    @JsonProperty("updated_by")
    private SimplifiedAppUserDto updatedBy;
}
