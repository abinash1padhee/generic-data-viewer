package com.dbs.services.rest.controller;

import com.dbs.services.rest.entity.Employee;
import com.dbs.services.rest.entity.EmployeeFilterSpecification;
import com.dbs.services.rest.page.PageRequestBuilder;
import com.dbs.services.rest.service.EmployeesService;
import com.dbs.services.rest.v1.api.EmployeesApi;
import com.dbs.services.rest.v1.api.model.ApiModelEmployees;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Rest Controller implementing swagger spec codegen generated {@link EmployeesApi} 
 * 
 * 
 *
 */
@RestController
public class EmployeesController implements EmployeesApi {

	private final EmployeesService employeesService;

	private final EmployeeFilterSpecification employeeFilterSpecification;

	/**
	 * 
	 * Prefer Constructor Injection over field injection 
	 * 
	 * @param employeesService
	 * @param employeeFilterSpecification
	 */
	public EmployeesController(EmployeesService employeesService, EmployeeFilterSpecification employeeFilterSpecification) {
		super();
		this.employeesService = employeesService;
		this.employeeFilterSpecification = employeeFilterSpecification;
	}

	@Override
	public ResponseEntity<ApiModelEmployees> getEmployees(
			@ApiParam(value = "Query param for 'firstName' filter") @Valid @RequestParam(value = "firstName", required = false) String firstName,
			@ApiParam(value = "Query param for 'lastName' filter") @Valid @RequestParam(value = "lastName", required = false) String lastName,
			@ApiParam(value = "Query param for 'dob' filter") @Valid @RequestParam(value = "dob", required = false) String dob,
			@ApiParam(value = "Query param for 'joiningDate' filter") @Valid @RequestParam(value = "joiningDate", required = false) String joiningDate,
			@ApiParam(value = "Query param for 'salary' filter") @Valid @RequestParam(value = "salary", required = false) String salary,
			@ApiParam(value = "Query param for 'pageNumber'") @Valid @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@ApiParam(value = "Query param for 'pageSize'") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
			@ApiParam(value = "Query param for 'sort' criteria") @Valid @RequestParam(value = "sort", required = false) String sort) {

		
		//Forms the specification for attributes by Filter specification builder from EmployeeFilterSpecification
		//We can mix and match and, or, not conditions
		Specification<Employee> specs = Specifications
				//Exposed attributes in API swagger spec doesn't need to be same as Database table column names.
				.where(employeeFilterSpecification.getStringTypeSpecification("firstName", firstName))
				.and(employeeFilterSpecification.getStringTypeSpecification("lastName", lastName))
				.and(employeeFilterSpecification.getLongTypeSpecification("salary", salary))
				.and(employeeFilterSpecification.getDateTypeSpecification("dob", dob))
				.and(employeeFilterSpecification.getDateTypeSpecification("joiningDate", joiningDate));
		
		
		//This represents the Page config with sorting
		PageRequest pageRequest = PageRequestBuilder.getPageRequest(pageSize, pageNumber, sort);
		
		//Call the DAO with specifications and pagerequest
		ApiModelEmployees employees = employeesService.getEmployees(specs, pageRequest);
		
		//Return the sorting criteria back so that the consumer can pass the same sorting or of different sorting based on the usecases.
		employees.getPaging().setSortingCriteria(sort);

		return new ResponseEntity<>(employees, HttpStatus.OK);

	}

}
