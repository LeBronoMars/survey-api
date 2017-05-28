package com.avinnovz.survey.models;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by rsbulanon on 4/14/17.
 */
@Data
@Entity
public class Department extends BaseModel {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true)
    private String description;

    @OneToMany
    @Fetch(value = FetchMode.JOIN)
    private List<AppUser> members;
}
