package com.avinnovz.survey.dto.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 6/3/17.
 */
@Data
public class CreateAnswerDto {

    @JsonProperty("question_id")
    private String questionId;

    @JsonProperty("question_mode")
    private String questionMode;

    @JsonProperty("choice_id")
    private String choiceId;

    private String answer;
}
