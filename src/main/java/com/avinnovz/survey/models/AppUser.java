package com.avinnovz.survey.models;

import com.avinnovz.survey.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 * Created by rsbulanon on 5/27/17.
 */
@Data
@Entity
public class AppUser extends BaseModel {

    @Column(unique = true, nullable = false)
    @JsonProperty("employee_no")
    @ApiModelProperty(example = "EMP-00001")
    private String employeeNo;

    @Column(nullable = false)
    @JsonProperty("first_name")
    @ApiModelProperty(example = "Ned")
    private String firstName;

    @Column(nullable = false)
    @JsonProperty("last_name")
    @ApiModelProperty(example = "Johnson")
    private String lastName;

    @Column(nullable = false)
    @JsonProperty("middle_name")
    @ApiModelProperty(example = "Flanders")
    private String middleName;

    @Column(nullable = false)
    @JsonProperty("address")
    @ApiModelProperty(example = "Hagonoy, Bulacan")
    private String address;

    @Column(nullable = false)
    @JsonProperty("contact_no")
    @ApiModelProperty(example = "09123456789")
    private String contactNo;

    @Column(unique = true, nullable = false)
    @ApiModelProperty(example = "ned@flanders.com")
    private String email;

    @Column(unique = true, nullable = false)
    @ApiModelProperty(example = "nedflanders")
    private String username;

    @Column(columnDefinition = "CHAR(15)", length = 30, nullable = false)
    @ApiModelProperty(example = "SUPER_ADMIN")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    @ApiModelProperty(example = "P@ssw0rd")
    private String password;

    @Column(columnDefinition = "CHAR(10)", length = 10, nullable = false)
    private String status;

    @Column(nullable = false)
    @JsonProperty("pic_url")
    private String picUrl;

    @Column(columnDefinition = "CHAR(6)", length = 6)
    @NotNull(message = "gender is required.")
    @ApiModelProperty(example = "Male")
    private String gender;
}
