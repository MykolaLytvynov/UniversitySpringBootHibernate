package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.entities.Lesson;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static java.util.Optional.ofNullable;

@Component
@Slf4j
@RequiredArgsConstructor
public class LessonDAO implements CrudOperations<Lesson, Integer> {
    private final SessionFactory sessionFactory;

    @Override
    public Lesson save(Lesson lesson) {
        log.debug("save('{}') called", lesson);
        Integer id;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            id = (Integer) session.save(lesson);
            session.getTransaction().commit();
        }
        lesson.setId(id);
        log.debug("save(position) returned '{}'", lesson);
        return lesson;
    }

    @Override
    public Optional<Lesson> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Lesson result;
        try (Session session = sessionFactory.openSession()) {
            result = session.get(Lesson.class, id);
        }
        log.debug("findById('{}') returned '{}'", id, result);
        return ofNullable(result);
    }


    @Override
    public List<Lesson> findAll() {
        log.debug("findAll() called");
        List<Lesson> result = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Lesson.class);
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
            Criteria criteria = session.createCriteria(Lesson.class);
            criteria.setProjection(Projections.rowCount());
            count = (Long) criteria.uniqueResult();
        }
        log.debug("count() returned '{}'", count);
        return count;
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        Lesson lesson = new Lesson();
        lesson.setId(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(lesson);
            session.getTransaction().commit();
        }
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Lesson lesson) {
        log.debug("delete('{}') called", lesson);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(lesson);
            session.getTransaction().commit();
        }
        log.debug("delete('{}') was success", lesson);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM lessons").executeUpdate();
            session.getTransaction().commit();
        }
        log.debug("deleteAll() was success");
    }

    @Override
    public void update(Lesson lesson) {
        log.debug("update('{}') called", lesson);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(lesson);
            session.getTransaction().commit();
        }
        log.debug("update('{}') was success", lesson);
    }

    public List<Lesson> getLessonsBetweenDatesForGroup(LocalDateTime startTime, LocalDateTime endTime, Integer idGroup) {
        log.debug("getLessonsBetweenDatesForGroup('{}', '{}', '{}') called", startTime, endTime, idGroup);
        List<Lesson> result = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Lesson.class);
            criteria.createAlias("groups", "groups");
            criteria.add(Restrictions.eq("groups.id", idGroup));
            criteria.add(Restrictions.between("dateTime", startTime, endTime));
            result = criteria.list();
        }
        log.debug("getLessonsBetweenDatesForGroup('{}', '{}', '{}') returned '{}'", startTime, endTime, idGroup, result);
        return result;
    }

    public List<Lesson> getLessonsBetweenDatesForTeacher(LocalDateTime startTime, LocalDateTime endTime, Integer idTeacher) {
        log.debug("getLessonsBetweenDatesForTeacher('{}', '{}', '{}') called", startTime, endTime, idTeacher);
        List<Lesson> result = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(Lesson.class);
            criteria.createAlias("subject", "subject");
            criteria.add(Restrictions.eq("subject.teacher.id", idTeacher));
            criteria.add(Restrictions.between("dateTime", startTime, endTime));
            result = criteria.list();
        }
        log.debug("getLessonsBetweenDatesForTeacher('{}', '{}', '{}') returned '{}'", startTime, endTime, idTeacher, result);
        return result;
    }
}
