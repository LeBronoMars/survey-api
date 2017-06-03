package com.avinnovz.survey.models.response;

import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.models.BaseModel;
import com.avinnovz.survey.models.Questionnaire;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by rsbulanon on 6/3/17.
 */
@Data
@Entity
public class Response extends BaseModel {

    @Column(nullable = false)
    @JsonProperty("first_name")
    @ApiModelProperty(example = "Juan")
    private String firstName;

    @Column(nullable = false)
    @JsonProperty("last_name")
    @ApiModelProperty(example = "Dela Cruz")
    private String lastName;

    @Column(nullable = false)
    @JsonProperty("middle_name")
    @ApiModelProperty(example = "Sanders")
    private String middleName;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("birth_date")
    @ApiModelProperty("yyyy-MM-dd")
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

    @Column(columnDefinition = "CHAR(6)", length = 6)
    @NotNull(message = "gender is required.")
    @ApiModelProperty(example = "Male")
    private String gender;

    @OneToOne
    @Fetch(value = FetchMode.JOIN)
    private Questionnaire questionnaire;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "response", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Answer> answers;

    @OneToOne
    @Fetch(value = FetchMode.JOIN)
    private AppUser conductedBy;
}
