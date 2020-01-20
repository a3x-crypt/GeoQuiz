package com.atriple.study.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0
    private var answered: Boolean = false //Challenge 3.1
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button) //Challenge Ch.2
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            if (answered) {
                currentIndex = (currentIndex + 1) % questionBank.size
                updateQuestion()
            } else Toast.makeText(this, "Finish the answer first!", Toast.LENGTH_SHORT).show()
        }

        //Challenge Chapter 2.1
        questionTextView.setOnClickListener {
            if (answered) {
                currentIndex = (currentIndex + 1) % questionBank.size
                updateQuestion()
            } else Toast.makeText(this, "Finish the answer first!", Toast.LENGTH_SHORT).show()
        }

        //Challenge Chapter 2.2
        prevButton.setOnClickListener {
            currentIndex = if (currentIndex == 0) {
                questionBank.size - 1
            } else currentIndex - 1

            updateQuestion()
        }
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

    private fun updateQuestion() {
        // Challenge 3, with Challenge 1 (Gravity.TOP Toast)
        if (currentIndex == 0) {
            var toast = Toast.makeText(
                this,
                "Your score: ${score * 100 / questionBank.size }%",
                Toast.LENGTH_LONG
            )
            toast.setGravity(Gravity.TOP, 0, 100)
            toast.show()
            score = 0
        }

        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        answered = false
    }

    private fun checkAnswer(userAnswer: Boolean) {
        //Challenge 3.1 - using Guard Clause
        if (answered) return

        val correctAnswer = questionBank[currentIndex].answer
        val isCorrect = userAnswer == correctAnswer

        val messageResId = if (isCorrect) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        if(isCorrect) score++

        answered = true
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

}
