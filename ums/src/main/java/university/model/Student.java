@Entity
@Table(name = "students")
public class Student extends User {
    @Column(name = "student_id", unique = true)
    private String studentId;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Grade> grades = new HashSet<>();
    
    @ManyToMany(mappedBy = "students")
    private Set<Subject> subjects = new HashSet<>();
    
    // Getters and setters
}
