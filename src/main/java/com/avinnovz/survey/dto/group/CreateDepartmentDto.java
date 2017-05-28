package com.avinnovz.survey.dto.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by rsbulanon on 5/27/17.
 */
@Data
public class CreateDepartmentDto {

    @ApiModelProperty(example = "Department 1")
    private String name;

    @ApiModelProperty(example = "Lorem ipsum dolor")
    private String description;

    @ApiModelProperty(example = "[{user_id}]")
    private List<String> members;

}
