package QuizApp.model.user;

import javax.persistence.*;
import org.hibernate.validator.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import QuizApp.model.quiz.Quiz;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Entity
@Table(name="tbl_users")
@Data
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int  userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Quiz> quizzes;
    public enum UserRole {
        USER, ADMIN
    }
}
