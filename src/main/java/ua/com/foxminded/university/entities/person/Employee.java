package ua.com.foxminded.university.entities.person;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ua.com.foxminded.university.entities.Position;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "employees")
public class Employee extends Person {

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Position position;
    @NonNull
    private Integer salary;
}
