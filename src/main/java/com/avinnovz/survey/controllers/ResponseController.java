package com.avinnovz.survey.controllers;

import com.avinnovz.survey.dto.response.CreateResponseDto;
import com.avinnovz.survey.dto.response.ResponseDto;
import com.avinnovz.survey.exceptions.CustomException;
import com.avinnovz.survey.exceptions.NotFoundException;
import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.models.Questionnaire;
import com.avinnovz.survey.models.response.Response;
import com.avinnovz.survey.services.AppUserService;
import com.avinnovz.survey.services.QuestionnaireService;
import com.avinnovz.survey.services.ResponseService;
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
 * Created by rsbulanon on 6/3/17.
 */
@Controller
@RequestMapping(path = "/api")
@Api(value = "Response Module")
public class ResponseController {
    private final Logger log = LoggerFactory.getLogger(QuestionsController.class);

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private ResponseService responseService;

    /**
     * create new response record
     */
    @RequestMapping(value = "/response",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<?> createResponse(@Valid @RequestBody CreateResponseDto createResponseDto,
                                            HttpServletRequest request) throws URISyntaxException {
        log.info("REST request to create new response : {}", createResponseDto);
        try {
            final AppUser appUser = appUserService.findByUsername(request.getRemoteUser());
            final Questionnaire questionnaire = questionnaireService.findOne(createResponseDto.getQuestionnaire());

            if (questionnaire == null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Questionnaire record not found."), HttpStatus.NOT_FOUND);
            } else {
                final Response newResponse = responseService.createResponse(createResponseDto, questionnaire, appUser);
                final ResponseDto responseDto = responseService.convert(newResponse);
                return ResponseEntity.created(new URI("/api/responses/" + newResponse.getId())).body(responseDto);
            }
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * get all responses
     */
    @RequestMapping(value = "/responses",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "page", value = "Used to paginate query results", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "Used to limit query results", dataType = "int", defaultValue = "20", paramType = "path"),
            @ApiImplicitParam(name = "sort", value = "Used to sort query results", dataType = "string", example = "email,asc", paramType = "path"),
    })
    public ResponseEntity<?> getAllResponses(Pageable pageable) throws URISyntaxException {
        final Page<ResponseDto> responseDtos = responseService.findAll(pageable);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    /**
     * get all responses conducted by
     */
    @RequestMapping(value = "/responses/conducted_by/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "page", value = "Used to paginate query results", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "Used to limit query results", dataType = "int", defaultValue = "20", paramType = "path"),
            @ApiImplicitParam(name = "sort", value = "Used to sort query results", dataType = "string", example = "email,asc", paramType = "path"),
    })
    public ResponseEntity<?> getAllResponsesConductedBy(@PathVariable String id, Pageable pageable) throws URISyntaxException {
        final AppUser appUser = appUserService.findOne(id);
        if (appUser == null) {
            return new ResponseEntity<>(Collections.singletonMap("message", "User not found"), HttpStatus.NOT_FOUND);
        } else {
            final Page<ResponseDto> responseDtos = responseService.findByConductedBy(appUser, pageable);
            return new ResponseEntity<>(responseDtos, HttpStatus.OK);
        }
    }

    /**
     * get all responses per questionnaire
     */
    @RequestMapping(value = "/responses/questionnaire/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "page", value = "Used to paginate query results", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "Used to limit query results", dataType = "int", defaultValue = "20", paramType = "path"),
            @ApiImplicitParam(name = "sort", value = "Used to sort query results", dataType = "string", example = "email,asc", paramType = "path"),
    })
    public ResponseEntity<?> getAllResponsesByQuestionnaire(@PathVariable String id, Pageable pageable) throws URISyntaxException {
        final Questionnaire questionnaire = questionnaireService.findOne(id);
        if (questionnaire == null) {
            return new ResponseEntity<>(Collections.singletonMap("message", "Questionnaire not found"), HttpStatus.NOT_FOUND);
        } else {
            final Page<ResponseDto> responseDtos = responseService.findByQuestionnaire(questionnaire, pageable);
            return new ResponseEntity<>(responseDtos, HttpStatus.OK);
        }
    }

}
