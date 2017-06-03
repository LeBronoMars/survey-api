package com.avinnovz.survey.models.response;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rsbulanon on 6/3/17.
 */
@Data
@Entity
public class Answer {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)", length = 36)
    protected String id;

    private String response;

    private String questionId;

    private String questionMode;

    private String choiceId;

    private String answer;
}
