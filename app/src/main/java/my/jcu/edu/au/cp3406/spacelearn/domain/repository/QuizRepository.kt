package my.jcu.edu.au.cp3406.spacelearn.domain.repository

import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizConfig
import my.jcu.edu.au.cp3406.spacelearn.domain.model.QuizQuestion

interface QuizRepository {

    fun getQuestions(
        config: QuizConfig
    ): List<QuizQuestion>
}