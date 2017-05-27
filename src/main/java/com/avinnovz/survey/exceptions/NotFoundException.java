package com.avinnovz.survey.exceptions;


import com.avinnovz.survey.models.BaseModel;

/**
 * Created by rsbulanon on 5/27/17.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(Class<? extends BaseModel> clazz, String id) {
        super("Resource " + clazz.getSimpleName() + " ID not found: " + id);
    }
}
