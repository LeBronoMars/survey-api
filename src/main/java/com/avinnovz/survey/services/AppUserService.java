package com.avinnovz.survey.services;


import com.avinnovz.survey.dto.user.AppUserDto;
import com.avinnovz.survey.dto.user.AppUserRegistrationDto;
import com.avinnovz.survey.exceptions.CustomException;
import com.avinnovz.survey.exceptions.NotFoundException;
import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by rsbulanon on 5/27/17.
 */
@Service
@Transactional
public class AppUserService {
    private final Logger log = LoggerFactory.getLogger(AppUserService.class);

    @Inject
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser createUser(final AppUserRegistrationDto appUserRegistrationDto) {
        final Optional<AppUser> existingUserByUsername = appUserRepository.findByUsername(appUserRegistrationDto.getUsername());

        if (existingUserByUsername.isPresent()) {
            throw new CustomException("Username: '" + appUserRegistrationDto.getUsername() + "' already in use.");
        } else {
            final Optional<AppUser> existingUserByEmail = appUserRepository.findByEmail(appUserRegistrationDto.getEmail());

            if (existingUserByEmail.isPresent()) {
                throw new CustomException("Email: '" + appUserRegistrationDto.getEmail() + "' already in use.");
            } else {
                final Optional<AppUser> existingUserByEmployeeNo = appUserRepository.findByEmployeeNo(appUserRegistrationDto.getEmployeeNo());

                if (existingUserByEmployeeNo.isPresent()) {
                    throw new CustomException("Employee No:' " + appUserRegistrationDto.getEmployeeNo() + "' already in use.");
                } else {
                    final AppUser newUser = new AppUser();
                    newUser.setId(null);
                    newUser.setEmployeeNo(appUserRegistrationDto.getEmployeeNo());
                    newUser.setFirstName(appUserRegistrationDto.getFirstName());
                    newUser.setMiddleName(appUserRegistrationDto.getMiddleName());
                    newUser.setLastName(appUserRegistrationDto.getLastName());
                    newUser.setAddress(appUserRegistrationDto.getAddress());
                    newUser.setContactNo(appUserRegistrationDto.getContactNo());
                    newUser.setEmail(appUserRegistrationDto.getEmail());
                    newUser.setUsername(appUserRegistrationDto.getUsername());
                    newUser.setRole(appUserRegistrationDto.getRole());
                    newUser.setGender(appUserRegistrationDto.getGender());
                    newUser.setPassword(passwordEncoder.encode(appUserRegistrationDto.getPassword()));

                    /** set default status to 'Active' */
                    newUser.setStatus("Active");

                    /** generate default Avatar */
                    final String encodedUsername = passwordEncoder.encode(appUserRegistrationDto.getEmail());
                    newUser.setPicUrl("http://www.gravatar.com/avatar/" + encodedUsername + "?d=identicon");

                    appUserRepository.save(newUser);
                    log.info("New user successfully created: {}", newUser);
                    return newUser;
                }
            }
        }
    }

    public Page<AppUser> findAll(Pageable pageable) {
        return appUserRepository.findAll(pageable);
    }

    public AppUser findOne(final String id) {
        return appUserRepository.findOne(id);
    }

    public AppUser findByUsername(String username) {
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);
        return appUser.map(user -> user).orElseThrow(() -> new NotFoundException(AppUser.class, username));
    }

    public AppUserDto convert(final AppUser appUser) {
        final AppUserDto userDto = new AppUserDto();
        userDto.setId(appUser.getId());
        userDto.setCreatedAt(appUser.getCreatedAt());
        userDto.setUpdatedAt(appUser.getUpdatedAt());
        userDto.setActive(appUser.getActive());
        userDto.setEmployeeNo(appUser.getEmployeeNo());
        userDto.setFirstName(appUser.getFirstName());
        userDto.setMiddleName(appUser.getMiddleName());
        userDto.setLastName(appUser.getLastName());
        userDto.setAddress(appUser.getAddress());
        userDto.setContactNo(appUser.getContactNo());
        userDto.setEmail(appUser.getEmail());
        userDto.setUsername(appUser.getUsername());
        userDto.setRole(appUser.getRole());
        userDto.setStatus(appUser.getStatus());
        userDto.setPicUrl(appUser.getPicUrl());
        userDto.setGender(appUser.getGender());
        userDto.setSynced(appUser.isSynced());
        return userDto;
    }

}
