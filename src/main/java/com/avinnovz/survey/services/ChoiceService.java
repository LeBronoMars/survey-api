package com.avinnovz.survey.services;

import com.avinnovz.survey.dto.choice.ChoiceDto;
import com.avinnovz.survey.dto.choice.CreateChoiceDto;
import com.avinnovz.survey.exceptions.CustomException;
import com.avinnovz.survey.models.Choice;
import com.avinnovz.survey.repositories.ChoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by rsbulanon on 5/28/17.
 */
@Service
public class ChoiceService {
    private final Logger log = LoggerFactory.getLogger(ChoiceService.class);

    @Autowired
    private ChoiceRepository choiceRepository;

    public Choice createChoice(final CreateChoiceDto createChoiceDto) {
        final Optional<Choice> existingChoice = choiceRepository.findByQuestionAndName(createChoiceDto.getQuestionId(), createChoiceDto.getName());

        if (existingChoice.isPresent()) {
            throw new CustomException("Choice : '" + createChoiceDto.getName() + "' already in use.");
        } else {
            final Choice choice = new Choice();
            choice.setId(null);
            choice.setName(createChoiceDto.getName());
            choice.setQuestion(createChoiceDto.getQuestionId());
            return choiceRepository.save(choice);
        }
    }

    public void delete(final Choice choice) {
        choiceRepository.delete(choice);
    }

    @Transactional
    public void deleteById(String id) {
        choiceRepository.deleteById(id);
    }

    public Choice findOne(final String id) {
        return choiceRepository.findOne(id);
    }

    public ChoiceDto convert(final Choice choice) {
        if (choice == null) {
            return null;
        } else {
            final ChoiceDto choiceDto = new ChoiceDto();
            choiceDto.setId(choice.getId());
            choiceDto.setCreatedAt(choice.getCreatedAt());
            choiceDto.setUpdatedAt(choice.getUpdatedAt());
            choiceDto.setActive(choice.getActive());
            choiceDto.setName(choice.getName());
            return choiceDto;
        }
    }
}
