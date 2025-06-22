@Entity
@Table(name = "teachers")
public class Teacher extends User {
    @Column(name = "teacher_id", unique = true)
    private String teacherId;
    
    @OneToMany(mappedBy = "teacher")
    private Set<Subject> taughtSubjects = new HashSet<>();
    
    // Getters and setters
}