package com.avinnovz.survey.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by rsbulanon on 5/27/17.
 */
@Data
public class AppUserLoginDto {

    @ApiModelProperty(example = "nedflanders")
    private String username;

    @ApiModelProperty(example = "P@ssw0rd")
    private String password;
}
