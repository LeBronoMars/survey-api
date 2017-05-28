package com.avinnovz.survey.repositories;

import com.avinnovz.survey.models.Question;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rsbulanon on 5/28/17.
 */
public interface QuestionRepository extends CrudRepository<Question, String> {
}
