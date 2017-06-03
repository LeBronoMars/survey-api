package com.avinnovz.survey.dto.response;

import com.avinnovz.survey.dto.answer.AnswerDto;
import com.avinnovz.survey.dto.questionnaire.QuestionnaireDto;
import com.avinnovz.survey.dto.questionnaire.SimplifiedQuestionnaireDto;
import com.avinnovz.survey.dto.user.AppUserDto;
import com.avinnovz.survey.dto.user.SimplifiedAppUserDto;
import com.avinnovz.survey.models.Questionnaire;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by rsbulanon on 6/3/17.
 */
@Data
public class ResponseDto {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("created_at")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("updated_at")
    private Date updatedAt;

    private Boolean active;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("middle_name")
    private String middleName;

    @JsonProperty("birth_date")
    private Date birthDate;

    private String address;

    @JsonProperty("contact_no")
    private String contactNo;

    private String email;

    private double latitude;

    private double longitude;

    private String gender;

    private SimplifiedQuestionnaireDto questionnaire;

    private List<AnswerDto> answers;

    @JsonProperty("conducted_by")
    private SimplifiedAppUserDto conductedBy;
}
