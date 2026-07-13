package my.jcu.edu.au.cp3406.spacelearn.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import my.jcu.edu.au.cp3406.spacelearn.domain.repository.QuizRepository

class QuizViewModelFactory(
    private val quizRepository: QuizRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                QuizViewModel::class.java
            )
        ) {
            return QuizViewModel(
                quizRepository = quizRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}