package com.avinnovz.survey.repositories;

import com.avinnovz.survey.models.AppUser;
import com.avinnovz.survey.models.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by rsbulanon on 5/27/17.
 */
public interface DepartmentRepository extends CrudRepository<Department, String> {

    Optional<Department> findByName(String name);

    Page<Department> findAll(Pageable pageable);

    List<Department> findByMembersIn(List<AppUser> appUsers);

    void deleteByMembersIn(List<AppUser> appUsers);
}
