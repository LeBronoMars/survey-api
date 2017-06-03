package com.avinnovz.survey.dto.response;

import com.avinnovz.survey.dto.answer.CreateAnswerDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by rsbulanon on 6/3/17.
 */
@Data
public class CreateResponseDto {

    @JsonProperty("first_name")
    @ApiModelProperty(example = "Juan")
    private String firstName;

    @JsonProperty("last_name")
    @ApiModelProperty(example = "Dela Cruz")
    private String lastName;

    @JsonProperty("middle_name")
    @ApiModelProperty(example = "Sanders")
    private String middleName;

    @JsonProperty("birth_date")
    @ApiModelProperty(example = "1988-09-25")
    private Date birthDate;

    @JsonProperty("address")
    @ApiModelProperty(example = "Hagonoy, Bulacan")
    private String address;

    @JsonProperty("contact_no")
    @ApiModelProperty(example = "09123456789")
    private String contactNo;

    @ApiModelProperty(example = "juandelacruz@gmail.com")
    private String email;

    @ApiModelProperty(example = "14.873465")
    private double latitude;

    @ApiModelProperty(example = "120.768921")
    private double longitude;

    @ApiModelProperty(example = "Male")
    private String gender;

    @NotNull
    private String questionnaire;

    private List<CreateAnswerDto> answers;
}
