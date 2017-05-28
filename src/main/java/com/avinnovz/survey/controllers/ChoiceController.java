package com.avinnovz.survey.controllers;

import com.avinnovz.survey.dto.choice.CreateChoiceDto;
import com.avinnovz.survey.dto.department.DepartmentDto;
import com.avinnovz.survey.enums.QuestionType;
import com.avinnovz.survey.exceptions.CustomException;
import com.avinnovz.survey.exceptions.NotFoundException;
import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.models.Choice;
import com.avinnovz.survey.models.Question;
import com.avinnovz.survey.services.AppUserService;
import com.avinnovz.survey.services.ChoiceService;
import com.avinnovz.survey.services.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Controller
@RequestMapping(path = "/api")
@Api(value = "Choice Module")
public class ChoiceController {
    private final Logger log = LoggerFactory.getLogger(ChoiceController.class);

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ChoiceService choiceService;

    /**
     * create new choice
     */
    @RequestMapping(value = "/choice",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<?> createChoice(@Valid @RequestBody CreateChoiceDto createChoiceDto) throws URISyntaxException {
        log.info("REST request to create new choice : {}", createChoiceDto);
        try {
            final Question question = questionService.findOne(createChoiceDto.getQuestionId());

            if (question == null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Question record not found."), HttpStatus.NOT_FOUND);
            } else {
                if (question.getQuestionType() == QuestionType.INPUT) {
                    return new ResponseEntity<>(Collections.singletonMap("message", "Creating of choice is for questions with question type of MULTIPLE_CHOICES only."), HttpStatus.BAD_REQUEST);
                } else {
                    choiceService.createChoice(createChoiceDto);
                    return new ResponseEntity<>(Collections.singletonMap("message", "Choice created."), HttpStatus.CREATED);
                }
            }
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * update existing choice
     */
    @RequestMapping(value = "/choice/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<?> updateChoice(@PathVariable String id, @Valid @RequestBody CreateChoiceDto createChoiceDto) throws URISyntaxException {
        log.info("REST request to create new choice : {}", createChoiceDto);
        try {
            final Question question = questionService.findOne(createChoiceDto.getQuestionId());

            if (question == null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Question record not found."), HttpStatus.NOT_FOUND);
            } else {
                if (question.getQuestionType() == QuestionType.INPUT) {
                    return new ResponseEntity<>(Collections.singletonMap("message", "Creating of choice is for questions with question type of MULTIPLE_CHOICES only."), HttpStatus.BAD_REQUEST);
                } else {
                    final Choice existingChoice = choiceService.findOne(id);
                    if (existingChoice == null) {
                        return new ResponseEntity<>(Collections.singletonMap("message", "Choice record not found."), HttpStatus.NOT_FOUND);
                    } else {
                        choiceService.updateChoice(createChoiceDto, existingChoice);
                        return new ResponseEntity<>(Collections.singletonMap("message", "Choice updated."), HttpStatus.CREATED);
                    }
                }
            }
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * delete choice
     * */
    @RequestMapping(value = "/choice/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<?> deleteChoice(@PathVariable String id) throws URISyntaxException {
        Choice choice = choiceService.findOne(id);
        return Optional.ofNullable(choice)
                .map(result -> {
                    choiceService.delete(choice);
                    return new ResponseEntity<>(Collections.singletonMap("message", "Choice successfully deleted."), HttpStatus.OK);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    /**
     * get choice by id
     * */
    @RequestMapping(value = "/choices/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<Choice> getChoiceById(@PathVariable String id) {
        log.info("REST request to get Department : {}", id);
        final Choice choice = choiceService.findOne(id);
        return Optional.ofNullable(choice)
                .map(result -> new ResponseEntity<>(choice, HttpStatus.OK))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
