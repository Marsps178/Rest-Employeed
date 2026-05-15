package cibertec.pe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cibertec.pe.dto.EmployeeRequest;
import cibertec.pe.dto.EmployeeResponse;
import cibertec.pe.entity.Employee;
import cibertec.pe.exception.ResourceNotFoundException;
import cibertec.pe.mapper.EmployeeMapper;
import cibertec.pe.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    public EmployeeService(EmployeeRepository repository, EmployeeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponse> findAll(Pageable pageable) {
        log.info("Fetching employees page {} size {}", pageable.getPageNumber(), pageable.getPageSize());
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public EmployeeResponse findById(Long id) {
        log.info("Fetching employee by id: {}", id);
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponse> findByDepartment(String department, Pageable pageable) {
        log.info("Fetching employees by department: {}", department);
        return repository.findByDepartment(department, pageable).map(mapper::toResponse);
    }

    public EmployeeResponse create(EmployeeRequest request) {
        log.info("Creating employee: {}", request.getEmail());
        Employee employee = mapper.toEntity(request);
        Employee saved = repository.save(employee);
        log.info("Employee created with id: {}", saved.getId());
        return mapper.toResponse(saved);
    }

    public EmployeeResponse update(Long id, EmployeeRequest request) {
        log.info("Updating employee with id: {}", id);
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        mapper.updateEntity(request, existing);
        Employee saved = repository.save(existing);
        log.info("Employee updated with id: {}", saved.getId());
        return mapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        log.info("Deleting employee with id: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        repository.deleteById(id);
        log.info("Employee deleted with id: {}", id);
    }
}
