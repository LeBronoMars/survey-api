package com.avinnovz.survey.repositories;

import com.avinnovz.survey.models.response.Response;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rsbulanon on 6/3/17.
 */
public interface ResponseRepository extends CrudRepository<Response, String> {
}
