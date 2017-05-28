package com.avinnovz.survey.services;

import com.avinnovz.survey.dto.questionnaire.CreateQuestionnaireDto;
import com.avinnovz.survey.dto.questionnaire.QuestionnaireDto;
import com.avinnovz.survey.dto.questions.QuestionDto;
import com.avinnovz.survey.exceptions.CustomException;
import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.models.Department;
import com.avinnovz.survey.models.Question;
import com.avinnovz.survey.models.Questionnaire;
import com.avinnovz.survey.repositories.QuestionnaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    private QuestionService questionService;

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
            questionnaire.setUpdatedBy(appUser);
            return questionnaireRepository.save(questionnaire);
        }
    }

    public Questionnaire updateQuestionnaire(final Questionnaire questionnaire,  CreateQuestionnaireDto createQuestionnaireDto,
                                             final Department department, final AppUser appUser) {
        final Optional<Questionnaire> existingQuestionnaire = questionnaireRepository.findByDepartmentAndName(
                department, createQuestionnaireDto.getName());

        if (existingQuestionnaire.isPresent()) {
            throw new CustomException("Questionnaire : '" + createQuestionnaireDto.getName() + "' already in use.");
        } else {
            questionnaire.setName(createQuestionnaireDto.getName());
            questionnaire.setDescription(createQuestionnaireDto.getDescription());
            questionnaire.setDepartment(department);
            questionnaire.setUpdatedBy(appUser);
            return questionnaireRepository.save(questionnaire);
        }
    }

    public Questionnaire findOne(final String id) {
        return questionnaireRepository.findOne(id);
    }

    public Page<QuestionnaireDto> findAll(Pageable pageable) {
        return questionnaireRepository.findAll(pageable).map(source -> convert(source));
    }

    public Page<QuestionnaireDto> findByDepartment(Department department, Pageable pageable) {
        return questionnaireRepository.findByDepartment(department, pageable).map(source -> convert(source));
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

            final Set<QuestionDto> questionDtos = new LinkedHashSet<>();
            if (questionnaire.getQuestions() != null && !questionnaire.getQuestions().isEmpty()) {
                log.info("QUESTIONS SIZE ---> " + questionnaire.getQuestions().size());
                for (Question q : questionnaire.getQuestions()) {
                    questionDtos.add(questionService.convert(q));
                }
            } else {
                log.info("QUESTIONS IS EMPTY ");
            }
            questionnaireDto.setQuestions(questionDtos);
            questionnaireDto.setCreatedBy(appUserService.convert(questionnaire.getCreatedBy()));
            questionnaireDto.setUpdatedBy(appUserService.convert(questionnaire.getUpdatedBy()));
            questionnaireDto.setDepartment(departmentService.convert(questionnaire.getDepartment()));
            return questionnaireDto;
        }
    }
}
