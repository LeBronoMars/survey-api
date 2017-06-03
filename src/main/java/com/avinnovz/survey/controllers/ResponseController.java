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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

}
