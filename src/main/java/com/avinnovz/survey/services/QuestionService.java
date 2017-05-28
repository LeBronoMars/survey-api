package com.avinnovz.survey.services;

import com.avinnovz.survey.dto.questionnaire.CreateQuestionnaireDto;
import com.avinnovz.survey.dto.questions.CreateQuestionDto;
import com.avinnovz.survey.dto.questions.QuestionDto;
import com.avinnovz.survey.exceptions.CustomException;
import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.models.Department;
import com.avinnovz.survey.models.Question;
import com.avinnovz.survey.models.Questionnaire;
import com.avinnovz.survey.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AppUserService appUserService;

    public Question createQuestion(final CreateQuestionDto createQuestionDto, final Questionnaire questionnaire,
                             final AppUser appUser) {
        final Optional<Question> existingQuestionnaire = questionRepository.findByQuestionnaireAndName(questionnaire, createQuestionDto.getName());

        if (existingQuestionnaire.isPresent()) {
            throw new CustomException("Question : '" + createQuestionDto.getName() + "' already in use.");
        } else {
            final Question question = new Question();
            question.setId(null);
            question.setName(createQuestionDto.getName());
            question.setQuestionType(createQuestionDto.getQuestionType());
            question.setQuestionnaire(questionnaire);
            question.setCreatedBy(appUser);
            question.setUpdatedBy(appUser);
            return questionRepository.save(question);
        }
    }

    public QuestionDto convert(final Question question) {
        if (question == null) {
            return null;
        } else {
            final QuestionDto questionDto = new QuestionDto();
            questionDto.setId(question.getId());
            questionDto.setCreatedAt(question.getCreatedAt());
            questionDto.setUpdatedAt(question.getUpdatedAt());
            questionDto.setActive(question.getActive());
            questionDto.setName(question.getName());
            questionDto.setQuestionType(question.getQuestionType());
            questionDto.setCreatedBy(appUserService.convert(question.getCreatedBy()));
            questionDto.setUpdatedBy(appUserService.convert(question.getUpdatedBy()));
            return questionDto;
        }
    }

}
