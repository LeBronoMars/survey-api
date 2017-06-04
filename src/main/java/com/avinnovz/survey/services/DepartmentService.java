package com.avinnovz.survey.services;

import com.avinnovz.survey.dto.department.CreateDepartmentDto;
import com.avinnovz.survey.dto.department.DepartmentDto;
import com.avinnovz.survey.dto.user.AppUserDto;
import com.avinnovz.survey.dto.user.SimplifiedAppUserDto;
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

import javax.transaction.Transactional;
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
        final Optional<Department> existingDepartment = departmentRepository.findByNameAndActive(createDepartmentDto.getName(), true);

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
        return departmentRepository.findByActive(pageable, true);
    }

    public DepartmentDto findOne(final String id) {
        return convert(departmentRepository.findOne(id));
    }

    public Department findDepartmentById(final String departmentId) {
        return departmentRepository.findOne(departmentId);
    }

    public Department update(final Department department) {
        return departmentRepository.save(department);
    }

    @Transactional
    public void deleteByIdAndMembersIn(final String name, List<AppUser> appUsers) {
        departmentRepository.deleteByMembersIn(name, appUsers);
    }

    public void delete(final Department department) {
        departmentRepository.delete(department);
    }

    public List<DepartmentDto> findByMember(AppUser appUser) {
        final ArrayList<DepartmentDto> departmentDtos = new ArrayList<>();
        final ArrayList<AppUser> appUsers = new ArrayList<>();
        appUsers.add(appUser);
        for (Department d : departmentRepository.findByMembersIn(appUsers)) {
            departmentDtos.add(convert(d));
        }
        return departmentDtos;
    }

    public List<Department> findDepartmentsByMember(AppUser appUser) {
        final ArrayList<DepartmentDto> departmentDtos = new ArrayList<>();
        final ArrayList<AppUser> appUsers = new ArrayList<>();
        appUsers.add(appUser);
        return departmentRepository.findByMembersIn(appUsers);
    }

    public Optional<Department> findByNameAndIdNot(String name, String id) {
        return departmentRepository.findByNameAndIdNotAndActive(name, id, true);
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
            final ArrayList<SimplifiedAppUserDto> members = new ArrayList<>();
            for (AppUser appUser : department.getMembers()) {
                members.add(appUserService.simpleUser(appUser));
            }
            departmentDto.setMembers(members);
            return departmentDto;
        }
    }
}
