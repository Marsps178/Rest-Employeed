package cibertec.pe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cibertec.pe.entity.Employee;
import cibertec.pe.exception.ResourceNotFoundException;
import cibertec.pe.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Employee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public List<Employee> findByDepartment(String department) {
        return repository.findByDepartment(department);
    }

    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    public Employee update(Long id, Employee employee) {
        Employee existing = findById(id);
        existing.setFirstName(employee.getFirstName());
        existing.setLastName(employee.getLastName());
        existing.setEmail(employee.getEmail());
        existing.setPhone(employee.getPhone());
        existing.setSalary(employee.getSalary());
        existing.setDepartment(employee.getDepartment());
        return repository.save(existing);
    }

    public void deleteById(Long id) {
        Employee existing = findById(id);
        repository.delete(existing);
    }
}
