package QuizApp.quizObjectMapper;

import QuizApp.model.option.Option;
import QuizApp.model.option.OptionInput;
import QuizApp.model.option.OptionUpdate;
import QuizApp.model.question.Question;
import QuizApp.model.question.QuestionInput;
import QuizApp.model.question.QuestionUpdate;
import QuizApp.model.user.User;
import QuizApp.model.user.UserInput;
import QuizApp.model.user.UserUpdate;

public class QuizObjectMapper {
    public static User convertUserInputToModel(UserInput userInput){
        User modelUser = new User();
        modelUser.setUserName(userInput.getUserName());
        modelUser.setUserEmail(userInput.getUserEmail());
        modelUser.setUserPassword(userInput.getUserPassword());
        return modelUser;
    }

    public static User convertUserUpdateToModel(UserUpdate userUpdate){
        User modelUser = new User();
        modelUser.setUserName(userUpdate.getUserName());
        modelUser.setUserEmail(userUpdate.getUserEmail());
        modelUser.setUserPassword(userUpdate.getUserPassword());
        return modelUser;
    }

    public static Question convertQuestionInputToModel(QuestionInput questionInput){
        Question modelQuestion = new Question();
        modelQuestion.setQuesDetails(questionInput.getQuesDetails());
        return modelQuestion;
    }
    public static Question convertQuestionUpdateToModel(QuestionUpdate questionUpdate){
        Question modelQuestion = new Question();
        modelQuestion.setQuesDetails(questionUpdate.getQuesDetails());
        return modelQuestion;
    }
    public static Option convertOptionInputToModel(OptionInput optionInput){
        Option modelOption = new Option();
        modelOption.setOptionDetails(optionInput.getOptionDetails());
        modelOption.setIfCorrect(optionInput.isIfCorrect());
        modelOption.setQuestion(optionInput.getQuestion());
        return modelOption;
    }

    public static Option convertOptionUpdateToModel(OptionUpdate optionUpdate){
        Option modelOption = new Option();
        modelOption.setOptionDetails(optionUpdate.getOptionDetails());
        modelOption.setIfCorrect(optionUpdate.isIfCorrect());
        modelOption.setQuestion(optionUpdate.getQuestion());
        return modelOption;
    }
}
