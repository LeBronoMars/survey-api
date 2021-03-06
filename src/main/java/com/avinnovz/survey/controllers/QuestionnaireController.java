package com.avinnovz.survey.controllers;

import com.avinnovz.survey.dto.questionnaire.CreateQuestionnaireDto;
import com.avinnovz.survey.dto.questionnaire.QuestionnaireDto;
import com.avinnovz.survey.exceptions.CustomException;
import com.avinnovz.survey.exceptions.NotFoundException;
import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.models.Department;
import com.avinnovz.survey.models.Questionnaire;
import com.avinnovz.survey.services.AppUserService;
import com.avinnovz.survey.services.ChoiceService;
import com.avinnovz.survey.services.DepartmentService;
import com.avinnovz.survey.services.QuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Controller
@RequestMapping(path = "/api")
@Api(value = "Questionnaire Module")
public class QuestionnaireController {
    private final Logger log = LoggerFactory.getLogger(QuestionnaireController.class);

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private ChoiceService choiceService;

    /**
     * create new questionnaire record
     */
    @RequestMapping(value = "/questionnaire",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<?> createQuestionnaire(@Valid @RequestBody CreateQuestionnaireDto createQuestionnaireDto,
                                                 HttpServletRequest request) throws URISyntaxException {
        log.info("REST request to create new questionnaire : {}", createQuestionnaireDto);
        try {

            final Department department = departmentService.findDepartmentById(createQuestionnaireDto.getDepartment());
            final AppUser appUser = appUserService.findByUsername(request.getRemoteUser());
            final Questionnaire questionnaire = questionnaireService.createQuestionnaire(createQuestionnaireDto, department, appUser);
            final QuestionnaireDto questionnaireDto = questionnaireService.convert(questionnaire);
            return ResponseEntity.created(new URI("/api/questionnaire/" + questionnaire.getId())).body(questionnaireDto);
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * get all questionnaire by department
     */
    @RequestMapping(value = "/questionnaires/department/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "page", value = "Used to paginate query results", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "Used to limit query results", dataType = "int", defaultValue = "20", paramType = "path"),
            @ApiImplicitParam(name = "sort", value = "Used to sort query results", dataType = "string", example = "email,asc", paramType = "path"),
    })
    public ResponseEntity<?> getQuestionnairesByDeparment(@PathVariable String id, Pageable pageable) throws URISyntaxException {
        final Department department = departmentService.findDepartmentById(id);
        if (department == null) {
            return new ResponseEntity<>(Collections.singletonMap("message", "Department record not found."), HttpStatus.NOT_FOUND);
        } else {
            final Page<QuestionnaireDto> questionnaireDtos = questionnaireService.findByDepartment(department, pageable);
            return new ResponseEntity<>(questionnaireDtos, HttpStatus.OK);
        }
    }

    /**
     * update existing questionnaire record
     */
    @RequestMapping(value = "/questionnaire/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<?> updateQuestionnaire(@PathVariable("id") String id,
                                                 @Valid @RequestBody CreateQuestionnaireDto createQuestionnaireDto,
                                                 HttpServletRequest request) throws URISyntaxException {
        final AppUser appUser = appUserService.findByUsername(request.getRemoteUser());
        log.info("REST request to update existing questionnaire : {}", createQuestionnaireDto);
        try {
            final Department department = departmentService.findDepartmentById(createQuestionnaireDto.getDepartment());

            final Questionnaire questionnaire = questionnaireService.updateQuestionnaire(questionnaireService.findOne(id),
                    createQuestionnaireDto, department, appUser);
            final QuestionnaireDto questionnaireDto = questionnaireService.convert(questionnaire);
            return ResponseEntity.created(new URI("/api/questionnaire/" + questionnaire.getId())).body(questionnaireDto);
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
        }
    }


    /**
     * get all questionnaire
     */
    @RequestMapping(value = "/questionnaires",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "page", value = "Used to paginate query results", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "Used to limit query results", dataType = "int", defaultValue = "20", paramType = "path"),
            @ApiImplicitParam(name = "sort", value = "Used to sort query results", dataType = "string", example = "email,asc", paramType = "path"),
    })
    public ResponseEntity<?> getAllQuestionaire(Pageable pageable) throws URISyntaxException {
        final Page<QuestionnaireDto> questionnaireDtos = questionnaireService.findAll(pageable);
        return new ResponseEntity<>(questionnaireDtos, HttpStatus.OK);
    }
}
