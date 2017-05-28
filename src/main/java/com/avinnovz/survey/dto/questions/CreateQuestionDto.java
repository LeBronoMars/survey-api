package com.avinnovz.survey.dto.questions;

import com.avinnovz.survey.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Data
public class CreateQuestionDto {

    @ApiModelProperty(example = "Question 1")
    private String name;

    @ApiModelProperty(example = "questionnaire_id")
    private String questionnaire;

    @ApiModelProperty(example = "MULTIPLE_CHOICES", allowableValues = "MULTIPLE_CHOICES, INPUT")
    @JsonProperty("question_type")
    private QuestionType questionType;
}
