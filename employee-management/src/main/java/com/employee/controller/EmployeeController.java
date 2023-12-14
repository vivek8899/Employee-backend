package com.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.exception.ResourceNotFoundException;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepo;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepo empRepo;

	// get all employees
	@GetMapping("/employees")
	public List<Employee> getAlllEmployee() {
		return empRepo.findAll();
	}

	// add employee
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee employee) {
		return empRepo.save(employee);
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee emp = empRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not existed with id" + id));
		return ResponseEntity.ok(emp);
	}

	@PutMapping("employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		Employee emp = empRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not existed with id" + id));

		emp.setEmailid(employee.getEmailid());
		emp.setFirstname(employee.getFirstname());
		emp.setLastname(employee.getLastname());

		Employee updatedEmployee = empRepo.save(emp);

		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("employees/{id}")
	public Map<String, Boolean> deleteEmployeeById(@PathVariable Long id){
		Employee emp = empRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not existed with id" + id));
		
		empRepo.delete(emp);
		Map<String , Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	
	
}
