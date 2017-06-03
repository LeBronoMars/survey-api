package com.avinnovz.survey.repositories;

import com.avinnovz.survey.models.response.Answer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rsbulanon on 6/3/17.
 */
public interface AnswerRepository extends CrudRepository<Answer, String> {
}
