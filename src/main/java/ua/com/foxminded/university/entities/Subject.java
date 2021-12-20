package ua.com.foxminded.university.entities;

import lombok.*;
import ua.com.foxminded.university.entities.person.Employee;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    @Column(name = "amount_lessons")
    private Integer amountLessons;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee teacher;

}
