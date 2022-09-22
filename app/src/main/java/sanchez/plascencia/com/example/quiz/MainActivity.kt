package sanchez.plascencia.com.example.quiz

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import sanchez.plascencia.com.example.quiz.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener{ view: View ->
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener{view: View->
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener{view: View ->
            quizViewModel.moveToNext()
            updateNextQuestion()
            enabled()
        }

        binding.prevButton.setOnClickListener{view: View ->
            quizViewModel.moveToPrev()
            updatePrevQuestion()
            enabled()
        }
    }
    private fun updateNextQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }
    private fun updatePrevQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) R.string.correct_text else R.string.incorrect_text
        val colorRes = if (userAnswer == correctAnswer) R.color.green else R.color.red
        //questionBank[currentIndex].enabled = 0
        quizViewModel.currentStatus = 0
        enabled()
        Snackbar.make(findViewById(R.id.true_button),messageResId,Snackbar.LENGTH_SHORT).
                setBackgroundTint(resources.getColor(colorRes)).show()

    }

    private fun enabled(){
        //questionBank[currentIndex].enabled == 1
        /*el ciclo está basado en el parametro enabled de quetionBank, si el valor
        de enabled es 1 los botones true y false se activan y si enabled
        es igual a 0 se desactivan*/
        if(quizViewModel.currentStatus == 1) {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }else{
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        }
        //currentIndex == 0
        if(quizViewModel.currentIndex == 0)
            binding.prevButton.isEnabled = false else binding.prevButton.isEnabled = true
        //currentIndex == questionBank.size - 1
        if(quizViewModel.currentIndex == quizViewModel.getCurrentSize - 1)
            binding.nextButton.isEnabled = false else binding.nextButton.isEnabled = true
    }

}

