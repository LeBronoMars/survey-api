package com.avinnovz.survey.services;

import com.avinnovz.survey.dto.questions.QuestionDto;
import com.avinnovz.survey.models.Question;
import com.avinnovz.survey.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AppUserService appUserService;

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
