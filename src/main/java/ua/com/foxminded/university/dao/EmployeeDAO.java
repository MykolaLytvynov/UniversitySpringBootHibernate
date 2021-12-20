package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.person.Employee;


import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmployeeDAO implements CrudOperations<Employee, Integer> {
    private final SessionFactory sessionFactory;

    String getAllTeacher = "SELECT DISTINCT employees.id, employees.name, last_name, position_id, salary " +
            "FROM employees INNER JOIN subjects ON employees.id = subjects.employee_id";

    @Override
    public Employee save(Employee employee) {
        log.debug("save('{}') called", employee);
        Integer id;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            id = (Integer) session.save(employee);
            session.getTransaction().commit();
        }
        employee.setId(id);
        log.debug("save(employee) returned '{}'", employee);
        return employee;
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Employee result;
        try (Session session = sessionFactory.openSession()) {
            result = session.get(Employee.class, id);
        }
        log.debug("findById('{}') returned '{}'", id, result);
        return ofNullable(result);
    }


    @Override
    public List<Employee> findAll() {
        log.debug("findAll() called");
        List<Employee> result = null;
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Employee.class);
            result = criteria.list();
        }
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    @Override
    public long count() {
        log.debug("count() called");
        long count;
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Employee.class);
            criteria.setProjection(Projections.rowCount());
            count = (Long) criteria.uniqueResult();
        }
        log.debug("count() returned '{}'", count);
        return count;
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        Employee employee = new Employee();
        employee.setId(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(employee);
            session.getTransaction().commit();
        }
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Employee employee) {
        log.debug("delete('{}') called", employee);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(employee);
            session.getTransaction().commit();
        }
        log.debug("delete('{}') was success", employee);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM employees").executeUpdate();
            session.getTransaction().commit();
        }
        log.debug("deleteAll() was success");
    }


    @Override
    public void update(Employee employee) {
        log.debug("update('{}') called", employee);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(employee);
            session.getTransaction().commit();
        }
        log.debug("update('{}') was success", employee);
    }

    public List<Employee> getAllTeacher() {
        log.debug("getAllTeacher() called");
        List<Employee> result = null;
        try (Session session = sessionFactory.openSession()) {
            result = (List<Employee>) session.createSQLQuery(getAllTeacher).addEntity(Employee.class).getResultList();
        }
        log.debug("getAllTeacher() returned '{}'", result);
        return result;
    }
}
