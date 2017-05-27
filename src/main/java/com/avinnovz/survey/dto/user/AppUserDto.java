package com.avinnovz.survey.dto.user;

import com.avinnovz.survey.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by rsbulanon on 5/27/17.
 */
@Data
public class AppUserDto {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("created_at")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("updated_at")
    private Date updatedAt;

    private Boolean active;

    @JsonProperty("employee_no")
    private String employeeNo;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("middle_name")
    private String middleName;

    private String address;

    @JsonProperty("contact_no")
    private String contactNo;

    private String email;
    private String username;
    private UserRole role;
    private String status;

    @JsonProperty("pic_url")
    private String picUrl;
    private String gender;

    @JsonProperty("is_synced")
    private boolean isSynced;
}
