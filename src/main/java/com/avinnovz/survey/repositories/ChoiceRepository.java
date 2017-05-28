package com.avinnovz.survey.repositories;

import com.avinnovz.survey.models.Choice;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by rsbulanon on 5/28/17.
 */
public interface ChoiceRepository extends CrudRepository<Choice, String> {

    Optional<Choice> findByQuestionAndName(String question, String name);

    Optional<Choice> findByQuestionAndNameAndIdNot(String question, String name, String id);

    void deleteById(String id);
}
