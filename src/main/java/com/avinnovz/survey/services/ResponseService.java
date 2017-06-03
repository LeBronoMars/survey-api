package com.avinnovz.survey.services;

import com.avinnovz.survey.dto.answer.AnswerDto;
import com.avinnovz.survey.dto.answer.CreateAnswerDto;
import com.avinnovz.survey.dto.response.CreateResponseDto;
import com.avinnovz.survey.dto.response.ResponseDto;
import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.models.Questionnaire;
import com.avinnovz.survey.models.response.Answer;
import com.avinnovz.survey.models.response.Response;
import com.avinnovz.survey.repositories.AnswerRepository;
import com.avinnovz.survey.repositories.ResponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by rsbulanon on 6/3/17.
 */
@Service
public class ResponseService {
    private final Logger log = LoggerFactory.getLogger(ResponseService.class);

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ChoiceService choiceService;

    public Response createResponse(final CreateResponseDto createResponseDto, final Questionnaire questionnaire,
                                  final AppUser appUser) {
        final Response response = new Response();
        response.setId(null);
        response.setFirstName(createResponseDto.getFirstName());
        response.setMiddleName(createResponseDto.getMiddleName());
        response.setLastName(createResponseDto.getLastName());
        response.setBirthDate(createResponseDto.getBirthDate());
        response.setAddress(createResponseDto.getAddress());
        response.setContactNo(createResponseDto.getContactNo());
        response.setGender(createResponseDto.getGender());
        response.setEmail(createResponseDto.getEmail());
        response.setLatitude(createResponseDto.getLatitude());
        response.setLongitude(createResponseDto.getLongitude());
        response.setQuestionnaire(questionnaire);
        response.setGender(createResponseDto.getGender());
        response.setConductedBy(appUser);

        responseRepository.save(response);

        final ArrayList<Answer> answers = new ArrayList<>();
        for (CreateAnswerDto createAnswerDto : createResponseDto.getAnswers()) {
            final Answer answer = new Answer();
            answer.setId(null);
            answer.setAnswer(createAnswerDto.getAnswer());
            answer.setResponse(response.getId());

            if (createAnswerDto.getQuestionMode() != null) {
                answer.setQuestionId(createAnswerDto.getQuestionId());
                answer.setQuestionMode(createAnswerDto.getQuestionMode());
                answer.setChoiceId(createAnswerDto.getChoiceId());
            }
            answerRepository.save(answer);
            answers.add(answer);
        }
        response.setAnswers(answers);
        responseRepository.save(response);
        return response;
    }


    public ResponseDto convert(final Response response) {
        if (response == null) {
            return null;
        } else {
            final ResponseDto responseDto = new ResponseDto();
            responseDto.setId(response.getId());
            responseDto.setCreatedAt(response.getCreatedAt());
            responseDto.setUpdatedAt(response.getUpdatedAt());
            responseDto.setActive(response.getActive());
            responseDto.setFirstName(response.getFirstName());
            responseDto.setMiddleName(response.getMiddleName());
            responseDto.setLastName(response.getLastName());
            responseDto.setAddress(response.getAddress());
            responseDto.setContactNo(response.getContactNo());
            responseDto.setEmail(response.getEmail());
            responseDto.setBirthDate(response.getBirthDate());
            responseDto.setGender(response.getGender());
            responseDto.setLatitude(response.getLatitude());
            responseDto.setLongitude(response.getLongitude());

            final ArrayList<AnswerDto> answerDtos = new ArrayList<>();

            for (Answer answer : response.getAnswers()) {
                final AnswerDto answerDto = new AnswerDto();
                log.info("REST 1. QUESTION ID : {}", answer.getQuestionId());
                answerDto.setAnswer(answer.getAnswer());
                answerDto.setQuestionDto(questionService.convert(questionService.findOne(answer.getQuestionId())));
                log.info("REST 2. QUESTION NAME : {}", answerDto.getQuestionDto().getName());


                if (answer.getChoiceId() != null) {
                    answerDto.setQuestionMode(answer.getQuestionMode());
                    log.info("REST 3. CHOICE ID : {}", answer.getChoiceId());
                    answerDto.setChoice(choiceService.convert(choiceService.findOne(answer.getChoiceId())));
                    log.info("REST 4. CHOICE NAME : {}", answerDto.getAnswer());
                }
            }

            responseDto.setAnswers(answerDtos);
            responseDto.setConductedBy(appUserService.convert(response.getConductedBy()));
            return responseDto;
        }
    }
}