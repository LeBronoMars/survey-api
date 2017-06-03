package com.avinnovz.survey.dto.answer;

import com.avinnovz.survey.dto.choice.ChoiceDto;
import com.avinnovz.survey.dto.questions.QuestionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 6/3/17.
 */
@Data
public class AnswerDto {

    private QuestionDto questionDto;

    @JsonProperty("question_mode")
    private String questionMode;

    private ChoiceDto choice;

    private String answer;

}
