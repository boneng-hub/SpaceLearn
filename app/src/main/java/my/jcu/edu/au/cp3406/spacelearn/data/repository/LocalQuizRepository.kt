package my.jcu.edu.au.cp3406.spacelearn.data.repository

import my.jcu.edu.au.cp3406.spacelearn.data.local.LocalQuestionBank
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizQuestion
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.QuizRepository

class LocalQuizRepository : QuizRepository {

    override fun getQuestions(
        config: QuizConfig
    ): List<QuizQuestion> {
        val matchingQuestions =
            LocalQuestionBank.questions
                .filter { question ->
                    question.topic ==
                            config.topic &&
                            question.difficulty ==
                            config.difficulty
                }

        val orderedQuestions =
            if (config.randomiseQuestions) {
                matchingQuestions.shuffled()
            } else {
                matchingQuestions
            }

        return orderedQuestions.take(
            config.questionCount
        )
    }
}