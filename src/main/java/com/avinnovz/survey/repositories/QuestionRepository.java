package com.avinnovz.survey.repositories;

import com.avinnovz.survey.models.Question;
import com.avinnovz.survey.models.Questionnaire;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by rsbulanon on 5/28/17.
 */
public interface QuestionRepository extends CrudRepository<Question, String> {

    Optional<Question> findByQuestionnaireAndName(Questionnaire questionnaire, String name);
}
