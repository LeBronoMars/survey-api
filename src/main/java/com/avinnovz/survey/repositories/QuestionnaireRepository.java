package com.avinnovz.survey.repositories;

import com.avinnovz.survey.models.Department;
import com.avinnovz.survey.models.Questionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by rsbulanon on 5/28/17.
 */
public interface QuestionnaireRepository extends CrudRepository<Questionnaire, String> {

    Page<Questionnaire> findAll(Pageable pageable);

    Optional<Questionnaire> findByDepartmentAndName(Department department, String name);
}
