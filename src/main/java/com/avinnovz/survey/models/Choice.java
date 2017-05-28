package com.avinnovz.survey.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Data
@Entity
public class Choice extends BaseModel {

    @Column(nullable = false)
    private String name;

    private String question;
}
