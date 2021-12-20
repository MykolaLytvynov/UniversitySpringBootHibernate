package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.Subject;


import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
@Slf4j
@RequiredArgsConstructor
public class SubjectDAO implements CrudOperations<Subject, Integer> {
    private final SessionFactory sessionFactory;

    @Override
    public Subject save(Subject subject) {
        log.debug("save('{}') called", subject);
        Integer id;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            id = (Integer) session.save(subject);
            session.getTransaction().commit();
        }
        subject.setId(id);
        log.debug("save(employee) returned '{}'", subject);
        return subject;
    }

    @Override
    public Optional<Subject> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Subject result;
        try (Session session = sessionFactory.openSession()) {
            result = session.get(Subject.class, id);
        }
        log.debug("findById('{}') returned '{}'", id, result);
        return ofNullable(result);
    }


    @Override
    public List<Subject> findAll() {
        log.debug("findAll() called");
        List<Subject> result = null;
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Subject.class);
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
            Criteria criteria = session.createCriteria(Subject.class);
            criteria.setProjection(Projections.rowCount());
            count = (Long) criteria.uniqueResult();
        }
        log.debug("count() returned '{}'", count);
        return count;
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        Subject subject = new Subject();
        subject.setId(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(subject);
            session.getTransaction().commit();
        }
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Subject subject) {
        log.debug("delete('{}') called", subject);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(subject);
            session.getTransaction().commit();
        }
        log.debug("delete('{}') was success", subject);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM subjects").executeUpdate();
            session.getTransaction().commit();
        }
        log.debug("deleteAll() was success");
    }

    @Override
    public void update(Subject subject) {
        log.debug("update('{}') called", subject);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(subject);
            session.getTransaction().commit();
        }
        log.debug("update('{}') was success", subject);
    }

    public List<Subject> getAllSubjectsOneTeacher(Integer id) {
        log.debug("getAllSubjectsOneTeacher('{}') called", id);
        List<Subject> result;
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Subject.class, "s")
                    .add(Restrictions.eq("s.teacher.id", id));
            result = criteria.list();
        }
        log.debug("update('{}') returned '{}'", id, result);
        return result;
    }

    public List<Subject> getSubjectsWithoutTeacher() {
        log.debug("getSubjectsWithoutTeacher() called");
        List<Subject> result;
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Subject.class, "s")
                    .add(Restrictions.isNull("s.teacher"));
            result = criteria.list();
        }
        log.debug("getSubjectsWithoutTeacher() returned '{}'", result);
        return result;
    }
}
