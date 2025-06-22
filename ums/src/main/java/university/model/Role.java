@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
    
    // Getters and setters
}

public enum ERole {
    ROLE_STUDENT,
    ROLE_TEACHER,
    ROLE_ADMIN
}