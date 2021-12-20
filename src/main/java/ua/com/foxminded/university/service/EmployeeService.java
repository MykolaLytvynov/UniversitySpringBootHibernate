package ua.com.foxminded.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.EmployeeDAO;
import ua.com.foxminded.university.dto.TeacherDto;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeDAO employeeDAO;
    private final SubjectService subjectService;

    public Employee save(Employee employee) {
        log.debug("save('{}') called", employee);
        Employee result = employeeDAO.save(employee);
        log.debug("save('{}') returned '{}'", employee, result);
        return result;
    }

    public Employee findById(Integer id) {
        log.debug("called :: findById('{}')", id);
        NotFoundException notFoundException = new NotFoundException("Employee not found by id = " + id);
        Employee result = employeeDAO.findById(id).orElse(null);
        if (result == null) throw notFoundException;
        result.setName(result.getName().trim());
        result.setLastName(result.getLastName().trim());
        log.debug("findById('{}') returned '{}'", id, result);
        return result;
    }


    public List<Employee> findAll() {
        log.debug("findAll() called");
        List<Employee> result = employeeDAO.findAll();
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    public long count() {
        log.debug("count() called");
        long result = employeeDAO.count();
        log.debug("count() returned '{}'", result);
        return result;
    }

    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        employeeDAO.deleteById(id);
        log.debug("deleteById('{}') was success", id);
    }

    public void delete(Employee employee) {
        log.debug("delete('{}') called", employee);
        employeeDAO.delete(employee);
        log.debug("delete('{}') was success", employee);
    }

    public void deleteAll() {
        log.debug("deleteAll() called");
        employeeDAO.deleteAll();
        log.debug("deleteAll() was success");
    }


    public void update(Employee employee) {
        log.debug("update('{}') called", employee);
        employeeDAO.update(employee);
        log.debug("update('{}') was success", employee);
    }

    public List<TeacherDto> getAllTeacher() {
        log.debug("getAllTeacher() called");
        List<TeacherDto> result = new ArrayList<>();
        List<Employee> teachers = employeeDAO.getAllTeacher();
        for (Employee teacher : teachers) {
            result.add(TeacherDto.builder().id(teacher.getId())
                    .name(teacher.getName())
                    .lastName(teacher.getLastName())
                    .position(teacher.getPosition())
                    .salary(teacher.getSalary())
                    .subjectList(subjectService.getAllSubjectsOneTeacher(teacher.getId()))
                    .build());
        }
        log.debug("getAllTeacher() returned '{}'", result);
        return result;
    }

}
