package QuizApp.repositories;

import QuizApp.model.option.Option;
import QuizApp.model.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option,Integer> {
    List<Option> findByQuestionQuesId(int questionId);

    @Query("SELECT o.question FROM Option o WHERE o.optionId = :optionId")
    Question findQuestionByOptionId(int optionId);
}
