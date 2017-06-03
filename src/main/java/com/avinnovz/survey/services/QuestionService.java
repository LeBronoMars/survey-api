package com.avinnovz.survey.services;

import com.avinnovz.survey.dto.choice.ChoiceDto;
import com.avinnovz.survey.dto.questions.CreateQuestionDto;
import com.avinnovz.survey.dto.questions.QuestionDto;
import com.avinnovz.survey.dto.questions.SimplifiedQuestionDto;
import com.avinnovz.survey.exceptions.CustomException;
import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.models.Choice;
import com.avinnovz.survey.models.Question;
import com.avinnovz.survey.models.Questionnaire;
import com.avinnovz.survey.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ChoiceService choiceService;

    public Question createQuestion(final CreateQuestionDto createQuestionDto, final Questionnaire questionnaire,
                             final AppUser appUser) {
        final Optional<Question> existingQuestionnaire = questionRepository.findByQuestionnaireAndNameAndQuestionType(
                createQuestionDto.getQuestionnaire(), createQuestionDto.getName(), createQuestionDto.getQuestionType());

        if (existingQuestionnaire.isPresent()) {
            throw new CustomException("Question : '" + createQuestionDto.getName() + "' already in use.");
        } else {
            final Question question = new Question();
            question.setId(null);
            question.setName(createQuestionDto.getName());
            question.setQuestionType(createQuestionDto.getQuestionType());
            question.setQuestionnaire(createQuestionDto.getQuestionnaire());
            question.setCreatedBy(appUser);
            question.setUpdatedBy(appUser);
            return questionRepository.save(question);
        }
    }

    public Question updateQuestion(final CreateQuestionDto createQuestionDto, final Questionnaire questionnaire,
                                   final AppUser appUser, final Question question) {
        final Optional<Question> existingQuestionnaire = questionRepository.findByQuestionnaireAndNameAndQuestionType(
                createQuestionDto.getQuestionnaire(), createQuestionDto.getName(), createQuestionDto.getQuestionType());

        if (existingQuestionnaire.isPresent()) {
            throw new CustomException("Question : '" + createQuestionDto.getName() + "' already in use.");
        } else {
            question.setName(createQuestionDto.getName());
            question.setQuestionType(createQuestionDto.getQuestionType());
            question.setQuestionnaire(createQuestionDto.getQuestionnaire());
            question.setUpdatedBy(appUser);
            return questionRepository.save(question);
        }
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public Question findOne(String id) {
        return questionRepository.findOne(id);
    }


    public void deleteQuestion(final Question question) {
        question.setActive(false);
        questionRepository.save(question);
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

            final Set<ChoiceDto> choiceDtos = new LinkedHashSet<>();
            if (question.getChoices() != null && !question.getChoices().isEmpty()) {
                for (Choice c : question.getChoices()) {
                    choiceDtos.add(choiceService.convert(c));
                }
            }
            questionDto.setChoices(choiceDtos);
            questionDto.setQuestionType(question.getQuestionType());
            questionDto.setCreatedBy(appUserService.simpleUser(question.getCreatedBy()));
            questionDto.setUpdatedBy(appUserService.simpleUser(question.getUpdatedBy()));
            return questionDto;
        }
    }

    public SimplifiedQuestionDto simpleQuestion(final Question question) {
        if (question == null) {
            return null;
        } else {
            final SimplifiedQuestionDto questionDto = new SimplifiedQuestionDto();
            questionDto.setId(question.getId());
            questionDto.setName(question.getName());
            questionDto.setQuestionType(question.getQuestionType());
            return questionDto;
        }
    }
}
