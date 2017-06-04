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

    Optional<Department> findByNameAndActive(String name, boolean isActive);

    Page<Department> findByActive(Pageable pageable, boolean isActive);

    List<Department> findByMembersIn(List<AppUser> appUsers);

    Optional<Department> findByNameAndIdNotAndActive(String name, String id, boolean isActive);

    void deleteByMembersIn(String id, List<AppUser> appUsers);
}
