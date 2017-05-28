package com.avinnovz.survey.models;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Data
@Entity
public class Questionnaire extends BaseModel {

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToOne
    @Fetch(value = FetchMode.JOIN)
    private Department department;

    @OneToOne
    @Fetch(value = FetchMode.JOIN)
    private AppUser createdBy;

    @OneToOne
    @Fetch(value = FetchMode.JOIN)
    private AppUser updatedBy;
}
