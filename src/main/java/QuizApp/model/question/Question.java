package QuizApp.model.question;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import QuizApp.model.option.Option;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tbl_question")
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int quesId;

    @Column(nullable = false)
    private String quesDetails;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;

    public Question() {
        this.options = new ArrayList<>();
    }
}
