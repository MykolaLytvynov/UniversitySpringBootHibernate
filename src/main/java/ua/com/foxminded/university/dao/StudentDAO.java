package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.person.Student;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;


@Slf4j
@Component
@RequiredArgsConstructor
public class StudentDAO implements CrudOperations<Student, Integer> {
    private final SessionFactory sessionFactory;


    @Override
    public Student save(Student student) {
        log.debug("save('{}')", student);
        Integer id;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            id = (Integer) session.save(student);
            session.getTransaction().commit();
        }
        student.setId(id);
        log.debug("save(student) returned '{}'", student);
        return student;
    }

    @Override
    public Optional<Student> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Student result;
        try (Session session = sessionFactory.openSession()) {
            result = session.get(Student.class, id);
        }
        log.debug("findById('{}') returned '{}'", id, result);
        return ofNullable(result);
    }


    @Override
    public List<Student> findAll() {
        log.debug("findAll() called");
        List<Student> result = null;
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Student.class);
            criteria.addOrder(Order.asc("last_name"));
            result = criteria.list();
        }
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    @Override
    public long count() {
        log.debug("count() called");
        long result;
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Student.class);
            criteria.setProjection(Projections.rowCount());
            result = (Long) criteria.uniqueResult();
        }
        log.debug("count() returned '{}'", result);
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        Student student = new Student();
        student.setId(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(student);
            session.getTransaction().commit();
        }
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Student student) {
        log.debug("delete('{}') called", student);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(student);
            session.getTransaction().commit();
        }
        log.debug("delete('{}') was success", student);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM students").executeUpdate();
            session.getTransaction().commit();
        }
        log.debug("deleteAll() was success");
    }

    @Override
    public void update(Student student) {
        log.debug("update('{}') called", student);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(student);
            session.getTransaction().commit();
        }
        log.debug("update('{}') was success", student);
    }
}
