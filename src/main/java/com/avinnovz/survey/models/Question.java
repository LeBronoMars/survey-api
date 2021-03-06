package com.avinnovz.survey.models;

import com.avinnovz.survey.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Data
@Entity
public class Question extends BaseModel {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "CHAR(30)", length = 30, nullable = false)
    @ApiModelProperty(example = "MULTIPLE_CHOICES")
    @Enumerated(EnumType.STRING)
    @JsonProperty("question_type")
    private QuestionType questionType;

    private String questionnaire;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Choice> choices;

    @OneToOne
    @Fetch(value = FetchMode.JOIN)
    private AppUser createdBy;

    @OneToOne
    @Fetch(value = FetchMode.JOIN)
    private AppUser updatedBy;
}
