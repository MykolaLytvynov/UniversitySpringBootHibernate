package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.Course;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseDAO implements CrudOperations<Course, Integer> {
    private final SessionFactory sessionFactory;

    @Override
    public Course save(Course course) {
        log.debug("save('{}') called", course);
        Integer id;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            id = (Integer) session.save(course);
            session.getTransaction().commit();
        }
        course.setId(id);
        log.debug("save(course) returned '{}'", course);
        return course;
    }

    @Override
    public Optional<Course> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Course result;
        try (Session session = sessionFactory.openSession()) {
            result = session.get(Course.class, id);
        }
        log.debug("findById('{}') returned '{}'", id, result);
        return ofNullable(result);
    }


    @Override
    public List<Course> findAll() {
        log.debug("findAll() called");
        List<Course> result = null;
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Course.class);
            criteria.addOrder(Order.asc("number_course"));
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
            Criteria criteria = session.createCriteria(Course.class);
            criteria.setProjection(Projections.rowCount());
            result = (Long) criteria.uniqueResult();
        }
        log.debug("count() returned '{}'", result);
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        Course course = new Course();
        course.setId(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(course);
            session.getTransaction().commit();
        }
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Course course) {
        log.debug("delete('{}') called", course);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(course);
            session.getTransaction().commit();
        }
        log.debug("delete('{}') was success", course);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM courses").executeUpdate();
            session.getTransaction().commit();
        }
        log.debug("deleteAll() was success");
    }

    @Override
    public void update(Course course) {
        log.debug("update('{}')", course);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(course);
            session.getTransaction().commit();
        }
        log.debug("update('{}') was success", course);
    }
}
