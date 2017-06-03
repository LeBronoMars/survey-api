package com.avinnovz.survey.repositories;

import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.models.Questionnaire;
import com.avinnovz.survey.models.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rsbulanon on 6/3/17.
 */
public interface ResponseRepository extends CrudRepository<Response, String> {

    Page<Response> findAll(Pageable pageable);

    Page<Response> findByConductedBy(AppUser appUser, Pageable pageable);

    Page<Response> findByQuestionnaire(Questionnaire questionnaire, Pageable pageable);

}
