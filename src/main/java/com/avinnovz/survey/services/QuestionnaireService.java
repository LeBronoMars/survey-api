package com.avinnovz.survey.services;

import com.avinnovz.survey.dto.questionnaire.CreateQuestionnaireDto;
import com.avinnovz.survey.dto.questionnaire.QuestionnaireDto;
import com.avinnovz.survey.exceptions.CustomException;
import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.models.Department;
import com.avinnovz.survey.models.Questionnaire;
import com.avinnovz.survey.repositories.QuestionnaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Service
public class QuestionnaireService {
    private final Logger log = LoggerFactory.getLogger(Questionnaire.class);

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private DepartmentService departmentService;

    public Questionnaire createQuestionnaire(final CreateQuestionnaireDto createQuestionnaireDto,
                                             final Department department, final AppUser appUser) {
        final Optional<Questionnaire> existingQuestionnaire = questionnaireRepository.findByDepartmentAndName(
                department, createQuestionnaireDto.getName());

        if (existingQuestionnaire.isPresent()) {
            throw new CustomException("Questionnaire : '" + createQuestionnaireDto.getName() + "' already in use.");
        } else {
            final Questionnaire questionnaire = new Questionnaire();
            questionnaire.setId(null);
            questionnaire.setName(createQuestionnaireDto.getName());
            questionnaire.setDescription(createQuestionnaireDto.getDescription());
            questionnaire.setDepartment(department);
            questionnaire.setCreatedBy(appUser);
            return questionnaireRepository.save(questionnaire);
        }
    }

    public QuestionnaireDto convert(final Questionnaire questionnaire) {
        if (questionnaire == null) {
            return null;
        } else {
            final QuestionnaireDto questionnaireDto = new QuestionnaireDto();
            questionnaireDto.setId(questionnaire.getId());
            questionnaireDto.setCreatedAt(questionnaire.getCreatedAt());
            questionnaireDto.setUpdatedAt(questionnaire.getUpdatedAt());
            questionnaireDto.setActive(questionnaire.getActive());
            questionnaireDto.setName(questionnaire.getName());
            questionnaireDto.setDescription(questionnaire.getDescription());
            questionnaireDto.setCreatedBy(appUserService.convert(questionnaire.getCreatedBy()));
            questionnaireDto.setDepartment(departmentService.convert(questionnaire.getDepartment()));
            return questionnaireDto;
        }
    }
}
