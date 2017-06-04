package com.avinnovz.survey.models;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rsbulanon on 4/14/17.
 */
@Data
@Entity
public class Department extends BaseModel {

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST })
    @Fetch(value = FetchMode.JOIN)
    private List<AppUser> members;
}
