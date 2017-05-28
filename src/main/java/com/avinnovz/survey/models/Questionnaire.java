package com.avinnovz.survey.models;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

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

    @OneToMany
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<Question> questions;

    @OneToOne
    @Fetch(value = FetchMode.JOIN)
    private AppUser createdBy;

    @OneToOne
    @Fetch(value = FetchMode.JOIN)
    private AppUser updatedBy;
}
