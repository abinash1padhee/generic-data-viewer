package com.dbs.services.rest.repository;

import com.dbs.services.rest.entity.Employee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * Spring JPA Repository with {@link JpaSpecificationExecutor}
 * 
 */
@Repository
public interface EmployeesRepository extends CrudRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

}
