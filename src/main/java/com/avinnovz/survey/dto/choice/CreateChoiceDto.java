package com.avinnovz.survey.dto.choice;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Data
public class CreateChoiceDto {

    @ApiModelProperty(example = "Choice 1")
    private String name;

    @ApiModelProperty(example = "question_id")
    @JsonProperty("question_id")
    private String questionId;
}
