package ua.com.foxminded.university.entities.person;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ua.com.foxminded.university.entities.Group;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "students")
public class Student extends Person {
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Group group;
}
