package com.avinnovz.survey.services;

import com.avinnovz.survey.dto.group.CreateDepartmentDto;
import com.avinnovz.survey.dto.group.DepartmentDto;
import com.avinnovz.survey.dto.user.AppUserDto;
import com.avinnovz.survey.exceptions.CustomException;
import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.models.Department;
import com.avinnovz.survey.repositories.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by rsbulanon on 5/27/17.
 */
@Service
public class DepartmentService {
    private final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AppUserService appUserService;

    public Department createDepartment(final CreateDepartmentDto createDepartmentDto) {
        final Optional<Department> existingDepartment = departmentRepository.findByName(createDepartmentDto.getName());

        if (existingDepartment.isPresent()) {
            throw new CustomException("Department name: '" + createDepartmentDto.getName() + "' already in use.");
        } else {
            final Department newDepartment = new Department();
            newDepartment.setName(createDepartmentDto.getName());
            newDepartment.setDescription(createDepartmentDto.getDescription());

            final List<AppUser> members = new ArrayList<>();

            if (createDepartmentDto.getMembers() != null && !createDepartmentDto.getMembers().isEmpty()) {
                for (String id : createDepartmentDto.getMembers()) {
                    final AppUser appUser = appUserService.findOne(id);
                    members.add(appUser);
                }
            }
            newDepartment.setMembers(members);
            return departmentRepository.save(newDepartment);
        }
    }

    public Page<Department> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    public DepartmentDto findOne(final String id) {
        return convert(departmentRepository.findOne(id));
    }

    public DepartmentDto convert(final Department department) {
        if (department == null) {
            return null;
        } else {
            final DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setId(department.getId());
            departmentDto.setCreatedAt(department.getCreatedAt());
            departmentDto.setUpdatedAt(department.getUpdatedAt());
            departmentDto.setActive(department.getActive());
            departmentDto.setName(department.getName());
            departmentDto.setDescription(department.getDescription());
            final ArrayList<AppUserDto> members = new ArrayList<>();
            for (AppUser appUser : department.getMembers()) {
                members.add(appUserService.convert(appUser));
            }
            departmentDto.setMembers(members);
            return departmentDto;
        }
    }
}