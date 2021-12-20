package ua.com.foxminded.university.entities;

import lombok.*;
import ua.com.foxminded.university.entities.person.Student;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @Column(name = "number_group")
    private Integer numberGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Course course;
    @OneToMany(mappedBy = "group")
    @ToString.Exclude
    private List<Student> students;

    @ManyToMany(mappedBy = "groups")
    @ToString.Exclude
    private List<Lesson> lessons;
}
