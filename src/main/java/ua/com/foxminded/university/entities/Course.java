package ua.com.foxminded.university.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @Column(name = "number_course")
    private Integer numberCourse;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Faculty faculty;

    @OneToMany(mappedBy = "course")
    @ToString.Exclude
    private List<Group> groups;

}
