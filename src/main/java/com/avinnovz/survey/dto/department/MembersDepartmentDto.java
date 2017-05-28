package com.avinnovz.survey.dto.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Data
public class MembersDepartmentDto {

    @JsonProperty("department_id")
    @ApiModelProperty(example = "deparment id")
    private String departmentId;

    @ApiModelProperty(example = "[{user_id}]")
    private List<String> members;

}
