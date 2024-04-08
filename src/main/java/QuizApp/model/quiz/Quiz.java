package QuizApp.model.quiz;

import QuizApp.model.question.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import QuizApp.model.user.User;

import java.util.List;

@Entity
@Table(name="tbl_quiz")
@Getter
@Setter
public class Quiz {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private long score;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Question> questions;

}