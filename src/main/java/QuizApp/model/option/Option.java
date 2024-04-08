package QuizApp.model.option;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import QuizApp.model.question.Question;

import java.io.Serializable;

@Entity
@Table(name="tbl_option")
@Getter
@Setter
public class Option implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int optionId;

    @Column(nullable = false)
    private String optionDetails;

    @Column(nullable = false)
    private boolean ifCorrect;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("options")
    private Question question;

}

