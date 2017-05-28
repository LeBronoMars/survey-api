package com.avinnovz.survey.controllers;

import com.avinnovz.survey.dto.group.CreateDepartmentDto;
import com.avinnovz.survey.dto.group.DepartmentDto;
import com.avinnovz.survey.dto.user.AppUserDto;
import com.avinnovz.survey.exceptions.CustomException;
import com.avinnovz.survey.exceptions.NotFoundException;
import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.models.Department;
import com.avinnovz.survey.services.AppUserService;
import com.avinnovz.survey.services.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by rsbulanon on 5/27/17.
 */
@Controller
@RequestMapping(path = "/api")
@Api(value = "Group Module")
public class DepartmentController {

    private final Logger log = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AppUserService appUserService;

    /**
     * create new department record
     */
    @RequestMapping(value = "/department",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<?> createDepartment(@Valid @RequestBody CreateDepartmentDto createDepartmentDto) throws URISyntaxException {
        log.info("REST request to create new group : {}", createDepartmentDto);
        try {
            final Department newDepartment = departmentService.createDepartment(createDepartmentDto);
            final DepartmentDto departmentDto = departmentService.findOne(newDepartment.getId());
            return ResponseEntity.created(new URI("/api/departments/" + newDepartment.getId())).body(departmentDto);
        } catch (CustomException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(Collections.singletonMap("message", e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * get department by id
     */
    @RequestMapping(value = "/departments/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<DepartmentDto> getById(@PathVariable String id) {
        log.info("REST request to get Department : {}", id);
        final DepartmentDto departmentDto = departmentService.findOne(id);
        return Optional.ofNullable(departmentDto)
                .map(result -> new ResponseEntity<>(departmentDto, HttpStatus.OK))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    /**
     * get department by id
     */
    @RequestMapping(value = "/my_departments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {{token}}",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<List<DepartmentDto>> getByMember(HttpServletRequest request) {
        return Optional.ofNullable(request.getRemoteUser()).map(user -> {
            final AppUser appUser = appUserService.findByUsername(user);
            final List<DepartmentDto> departmentDtos = departmentService.findByMember(appUser);
            return new ResponseEntity<>(departmentDtos, HttpStatus.OK);
        }).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }
}
