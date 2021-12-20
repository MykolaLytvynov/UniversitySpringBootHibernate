package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.Faculty;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;


@RequiredArgsConstructor
@Component
@Slf4j
public class FacultyDAO implements CrudOperations<Faculty, Integer> {
    private final SessionFactory sessionFactory;

    @Override
    public Faculty save(Faculty faculty) {
        log.debug("save('{}') called", faculty);
        Integer id;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            id = (Integer) session.save(faculty);
            session.getTransaction().commit();
        }
        faculty.setId(id);
        log.debug("save(faculty) was success. Returned '{}'", faculty);
        return faculty;
    }

    @Override
    public Optional<Faculty> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Faculty result;
        try (Session session = sessionFactory.openSession()) {
            result = session.get(Faculty.class, id);
        }
        log.debug("findById('{}') returned '{}'", id, result);
        return ofNullable(result);
    }


    @Override
    public List<Faculty> findAll() {
        log.debug("findAll() called");
        List<Faculty> result = null;
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Faculty.class);
            criteria.addOrder(Order.asc("name"));
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
            Criteria criteria = session.createCriteria(Faculty.class);
            criteria.setProjection(Projections.rowCount());
            result = (Long) criteria.uniqueResult();
        }
        log.debug("count() returned '{}'", result);
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(faculty);
            session.getTransaction().commit();
        }
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Faculty faculty) {
        log.debug("delete('{}') called", faculty);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(faculty);
            session.getTransaction().commit();
        }
        log.debug("delete('{}') was success", faculty);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM faculties").executeUpdate();
            session.getTransaction().commit();
        }
        log.debug("deleteAll() was success");
    }

    @Override
    public void update(Faculty faculty) {
        log.debug("update('{}') called", faculty);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(faculty);
            session.getTransaction().commit();
        }
        log.debug("update('{}') was success", faculty);
    }
}
