package com.avinnovz.survey.dto.questionnaire;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Data
public class CreateQuestionnaireDto {

    @ApiModelProperty(example = "Questionnaire 1")
    private String name;

    @ApiModelProperty(example = "Lorem ipsum dolor")
    private String description;

    @ApiModelProperty(example = "department_id")
    private String department;
}
