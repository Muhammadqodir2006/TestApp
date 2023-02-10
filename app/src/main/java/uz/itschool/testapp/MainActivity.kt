package uz.itschool.testapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private val testCount = 5
    private lateinit var tests: ArrayList<Question>
    private lateinit var questionTextView: TextView
    private lateinit var variants: LinearLayout
    private fun userAnswer(): Int {return currentQuestion().userAnswer}
    private var currentQuestionIndex = 0
    private fun currentQuestion (): Question {return tests[currentQuestionIndex]}




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //

        //     Intentlarni qabulqilish kerak

        //


        questionTextView = findViewById(R.id.questionText)
        variants = findViewById(R.id.variants)
        tests = getTests()
        initGrid()
        previous.isEnabled = currentQuestionIndex >0
        next.isEnabled = currentQuestionIndex < testCount-1
        varupdate()
        questupdate()

        var0Card.setOnClickListener{
            tests[currentQuestionIndex].userAnswer = if (tests[currentQuestionIndex].userAnswer == 0) -1 else 0
            varupdate()
            checkForEnd()
        }
        var1Card.setOnClickListener{
            tests[currentQuestionIndex].userAnswer = if (tests[currentQuestionIndex].userAnswer == 1) -1 else 1
            varupdate()
            checkForEnd()
        }
        var2Card.setOnClickListener{
            tests[currentQuestionIndex].userAnswer =if (tests[currentQuestionIndex].userAnswer == 2) -1  else 2
            varupdate()
            checkForEnd()
        }
        var3Card.setOnClickListener{
            tests[currentQuestionIndex].userAnswer = if (tests[currentQuestionIndex].userAnswer == 3) -1 else 3
            varupdate()
            checkForEnd()
        }
        previous.setOnClickListener {
            currentQuestionIndex--
            questupdate()
        }
        next.setOnClickListener {
            currentQuestionIndex++
            questupdate()
        }
        restart.setOnClickListener {
            reset()
        }

    }

    private fun reset() {
        game.isEnabled = true
        endView.visibility = View.GONE
        game.alpha = 1f
        currentQuestionIndex = 0
        tests = getTests()
        initGrid()
        previous.isEnabled = currentQuestionIndex >0
        next.isEnabled = currentQuestionIndex < testCount-1
        varupdate()
        questupdate()

    }

    @SuppressLint("SetTextI18n")
    private fun questupdate(){
        order.text = "${currentQuestionIndex+1}/${testCount }"
        questionTextView.text = currentQuestion().test.question

        var0.text = currentQuestion().test.vars[0]
        var1.text = currentQuestion().test.vars[1]
        var2.text = currentQuestion().test.vars[2]
        var3.text = currentQuestion().test.vars[3]

        previous.isEnabled = currentQuestionIndex >0
        next.isEnabled = currentQuestionIndex < testCount-1
        varupdate()
    }

    private fun varupdate() {


        if (userAnswer() == 0) var0Card.setBackgroundColor(Color.rgb(82,66,207)) else var0Card.setBackgroundColor(
            Color.rgb(225, 225, 225)
        )
        if (userAnswer() == 1) var1Card.setBackgroundColor(Color.rgb(82,66,207)) else var1Card.setBackgroundColor(
            Color.rgb(225, 225, 225)
        )
        if (userAnswer() == 2) var2Card.setBackgroundColor(Color.rgb(82,66,207)) else var2Card.setBackgroundColor(
            Color.rgb(225, 225, 225)
        )
        if (userAnswer() == 3) var3Card.setBackgroundColor(Color.rgb(82,66,207)) else var3Card.setBackgroundColor(
            Color.rgb(225, 225, 225)
        )

        if (userAnswer() == 0) var0.setTextColor(Color.WHITE) else var0.setTextColor(Color.BLACK)
        if (userAnswer() == 1) var1.setTextColor(Color.WHITE) else var1.setTextColor(Color.BLACK)
        if (userAnswer() == 2) var2.setTextColor(Color.WHITE) else var2.setTextColor(Color.BLACK)
        if (userAnswer() == 3) var3.setTextColor(Color.WHITE) else var3.setTextColor(Color.BLACK)

        initGrid()

    }

    private fun getTests(): ArrayList<Question> {
        val array = ArrayList<Question>()
        val randoms = ArrayList<Int>()
        val bazaSize = baza.size
        if (testCount == bazaSize) {
            for (i in baza) {
                array.add(Question(i))
            }
            return array
        }

        for (i in 1..testCount) {
            var randomNum = (0 until bazaSize).random()
            while (randoms.contains(randomNum)) {
                randomNum = (0 until bazaSize).random()
            }
            randoms.add(randomNum)
            val a = baza[randomNum]
            val vars = a.vars
            vars.shuffle()
            val test = Test(a.question, vars, a.correctAnswer)
            array.add(Question(test))

        }
        return array
    }
    private fun initGrid(){
        numbersgrid.removeAllViews()
        for (i in 1 .. testCount){
            val btn = Button(applicationContext)
            if (tests[i-1].userAnswer != -1) {
                btn.setBackgroundColor(Color.TRANSPARENT)
                btn.setTextColor(Color.WHITE)
            }
            btn.setOnClickListener{
                currentQuestionIndex = i-1
                questupdate()
            }
            btn.text = i.toString()
            numbersgrid.addView(btn)
        }

    }
    @SuppressLint("SetTextI18n")
    private fun checkForEnd() {
        for (i in tests){
            if (i.userAnswer == -1) return
        }
        val score = calculate()
        scoreView.text = "$score/$testCount"
        endView.visibility = View.VISIBLE
        game.alpha = 0.65f
        game.isEnabled = false


    }

    private fun calculate(): Int {
        var a = 0
        for (i in tests){
            if (i.test.correctAnswer == i.test.vars[i.userAnswer]) a++
        }
        return a
    }

}