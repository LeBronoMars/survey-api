package com.avinnovz.survey.controllers;

import com.avinnovz.survey.dto.questions.CreateQuestionDto;
import com.avinnovz.survey.dto.questions.QuestionDto;
import com.avinnovz.survey.exceptions.CustomException;
import com.avinnovz.survey.exceptions.NotFoundException;
import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.models.Choice;
import com.avinnovz.survey.models.Question;
import com.avinnovz.survey.models.Questionnaire;
import com.avinnovz.survey.services.*;
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
@Api(value = "Questionnaire Module")
public class QuestionsController {
    private final Logger log = LoggerFactory.getLogger(QuestionsController.class);

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ChoiceService choiceService;

    /**
     * create new question record
     */
    @RequestMapping(value = "/question",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<?> createQuestion(@Valid @RequestBody CreateQuestionDto createQuestionDto,
                                                 HttpServletRequest request) throws URISyntaxException {
        log.info("REST request to create new question : {}", createQuestionDto);
        try {
            final AppUser appUser = appUserService.findByUsername(request.getRemoteUser());
            final Questionnaire questionnaire = questionnaireService.findOne(createQuestionDto.getQuestionnaire());

            if (questionnaire == null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Questionnaire record not found."), HttpStatus.NOT_FOUND);
            } else {
                final Question newQuestion = questionService.createQuestion(createQuestionDto, questionnaire, appUser);
                final QuestionDto questionDto = questionService.convert(newQuestion);
                return ResponseEntity.created(new URI("/api/questions/" + newQuestion.getId())).body(questionDto);
            }
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * update existing question record
     */
    @RequestMapping(value = "/question/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<?> updateQuestion(@PathVariable String id, @Valid @RequestBody CreateQuestionDto createQuestionDto,
                                            HttpServletRequest request) throws URISyntaxException {
        log.info("REST request to create new question : {}", createQuestionDto);
        try {
            final Question existingQuestion = questionService.findOne(id);
            final AppUser appUser = appUserService.findByUsername(request.getRemoteUser());
            final Questionnaire questionnaire = questionnaireService.findOne(createQuestionDto.getQuestionnaire());

            if (questionnaire == null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Questionnaire record not found."), HttpStatus.NOT_FOUND);
            } else if (existingQuestion == null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Question record not found."), HttpStatus.NOT_FOUND);
            } else {
                final Question newQuestion = questionService.updateQuestion(createQuestionDto, questionnaire, appUser, existingQuestion);
                final QuestionDto questionDto = questionService.convert(newQuestion);
                return ResponseEntity.created(new URI("/api/questions/" + newQuestion.getId())).body(questionDto);
            }
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * delete question
     * */
    @RequestMapping(value = "/question/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<?> deleteQuestion(@PathVariable String id) throws URISyntaxException {
        Question question = questionService.findOne(id);
        return Optional.ofNullable(question)
                .map(result -> {
                    choiceService.deleteAllByQuestion(question.getId());
                    questionService.delete(question);
                    return new ResponseEntity<>(Collections.singletonMap("message", "Question successfully deleted."), HttpStatus.OK);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
