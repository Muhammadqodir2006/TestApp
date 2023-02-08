package uz.itschool.testapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val testCount = 15
    private lateinit var tests: ArrayList<Question>
    private lateinit var questionTextView: TextView
    private lateinit var variants: LinearLayout
    private var userAnswer = ""
    private var currentQuestionIndex = 0
    private var userAnswers = ArrayList<String>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for (i in 1..testCount){
            userAnswers.add("")
        }

        //

        //     Intentlarni qabulqilish kerak

        //

        questionTextView = findViewById(R.id.questionText)
        variants = findViewById(R.id.variants)
        tests = getTests()
        previous.isEnabled = currentQuestionIndex >0
        next.isEnabled = currentQuestionIndex < testCount-1
        varupdate()
        questupdate(0)
        Log.d("TAG", userAnswers.size.toString())

        var0Card.setOnClickListener{
            userAnswer = var0.text.toString()
            varupdate()
        }
        var1Card.setOnClickListener{
            userAnswer = var1.text.toString()
            varupdate()
        }
        var2Card.setOnClickListener{
            userAnswer = var2.text.toString()
            varupdate()
        }
        var3Card.setOnClickListener{
            userAnswer = var3.text.toString()
            varupdate()
        }
        previous.setOnClickListener {
            currentQuestionIndex--
            questupdate(-1)
        }
        next.setOnClickListener {
            currentQuestionIndex++
            questupdate(1)
        }

    }
    private fun questupdate(c:Int){
        if (userAnswer != "") {
            if (c == 1) userAnswers[currentQuestionIndex-1] = userAnswer
            if (c == -1) userAnswers[currentQuestionIndex+1] = userAnswer
        }
        userAnswer = ""
        val question = tests[currentQuestionIndex]

        questionTextView.text = question.test.question

        var0.text = question.test.vars[0]
        var1.text = question.test.vars[1]
        var2.text = question.test.vars[2]
        var3.text = question.test.vars[3]

        previous.isEnabled = currentQuestionIndex >0
        next.isEnabled = currentQuestionIndex < testCount-1
        varupdate()
    }

    private fun varupdate() {


        if (userAnswer == var0.text) var0Card.setBackgroundColor(Color.BLUE) else var0Card.setBackgroundColor(
            Color.rgb(225, 225, 225)
        )
        if (userAnswer == var1.text) var1Card.setBackgroundColor(Color.BLUE) else var1Card.setBackgroundColor(
            Color.rgb(225, 225, 225)
        )
        if (userAnswer == var2.text) var2Card.setBackgroundColor(Color.BLUE) else var2Card.setBackgroundColor(
            Color.rgb(225, 225, 225)
        )
        if (userAnswer == var3.text) var3Card.setBackgroundColor(Color.BLUE) else var3Card.setBackgroundColor(
            Color.rgb(225, 225, 225)
        )

        if (userAnswer == var0.text) var0.setTextColor(Color.WHITE) else var0.setTextColor(Color.BLACK)
        if (userAnswer == var1.text) var1.setTextColor(Color.WHITE) else var1.setTextColor(Color.BLACK)
        if (userAnswer == var2.text) var2.setTextColor(Color.WHITE) else var2.setTextColor(Color.BLACK)
        if (userAnswer == var3.text) var3.setTextColor(Color.WHITE) else var3.setTextColor(Color.BLACK)


//        for (i in question.test.vars){
//            val varCardView = CardView(this)
//            varCardView.marginTop
//            val text = TextView(applicationContext)
//            text.text = i
//            text.gravity = TextView.TEXT_ALIGNMENT_GRAVITY
//
//
//
//            varCardView.addView(text)
//            variants.addView(varCardView)
//        }

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
            var test = Test(a.question, vars, a.correctAnswer)
            array.add(Question(baza[randomNum]))

        }
        return array
    }
}