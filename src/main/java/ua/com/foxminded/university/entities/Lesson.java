package ua.com.foxminded.university.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @NonNull
    private Integer duration;
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private ClassRoom classRoom;
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Subject subject;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinTable(
            name = "groups_lessons",
            joinColumns = {@JoinColumn(name = "id_lesson")},
            inverseJoinColumns = {@JoinColumn(name = "id_group")}
    )
    List<Group> groups;

}
