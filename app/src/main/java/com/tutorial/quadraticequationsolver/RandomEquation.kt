package com.tutorial.quadraticequationsolver

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class RandomEquation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_equation)


        val rand_str_a: EditText = findViewById(R.id.RandomA)
        val rand_str_b: EditText = findViewById(R.id.RandomB)
        val rand_str_c: EditText = findViewById(R.id.RandomC)
        val zero: EditText = findViewById(R.id.zero)


        val RandomSol1: TextView = findViewById(R.id.RandomSol1)
        val RandomSol2: TextView = findViewById(R.id.RandomSol2)

        val generate: Button = findViewById(R.id.generate)
        val RandomClear: Button = findViewById(R.id.RandomClear)

        val realSwitch: Switch = findViewById(R.id.realSwitch)
        val imaginarySwitch: Switch = findViewById(R.id.imaginarySwitch)

        var real: Boolean = true
        var imaginary: Boolean = true


        realSwitch.isChecked = true
        imaginarySwitch.isChecked = true

        realSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                real = true
            }
            else{
                if (imaginarySwitch.isChecked==false){
                    imaginarySwitch.isChecked = true
                    imaginary = true
                }
                real = false
            }

        }

        imaginarySwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                imaginary = true
            }
            else{
                if(realSwitch.isChecked == false){
                    realSwitch.isChecked = true
                    real = true
                }
                imaginary = false
            }

        }

        val range: EditText = findViewById(R.id.RandomRange)
        val onTouchListener: View.OnTouchListener = View.OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val editText = view as EditText
                if (editText.text.toString() == "0") {
                    editText.text.clear()
                }
            }
            false // Return false to allow the event to continue processing
        }
        range.setOnTouchListener(onTouchListener)



        val buttonChange: Button = findViewById(R.id.buttonChange)

        rand_str_a.setInputType(InputType.TYPE_NULL);
        rand_str_a.isFocusable = false
        rand_str_a.isFocusableInTouchMode = false

        rand_str_b.setInputType(InputType.TYPE_NULL);
        rand_str_b.isFocusable = false
        rand_str_b.isFocusableInTouchMode = false

        rand_str_c.setInputType(InputType.TYPE_NULL);
        rand_str_c.isFocusable = false
        rand_str_c.isFocusableInTouchMode = false

        zero.setInputType(InputType.TYPE_NULL);
        zero.isFocusable = false
        zero.isFocusableInTouchMode = false



        generate.setOnClickListener {
            var A = 0
            var B = 0
            var C = 0
            var RangeText = range.getText().toString().toInt()
            if (RangeText<=0){
                Toast.makeText(this, "Random Range has to be positive", Toast.LENGTH_SHORT).show()
            }
            else{
                if(real == true && imaginary == true){
                    while(true){
                        A = Random.nextInt(-RangeText, RangeText+1)
                        B = Random.nextInt(-RangeText, RangeText+1)
                        C = Random.nextInt(-RangeText, RangeText+1)
                        if(A!=0 && B!=0 && C!=0){
                            break
                        }
                    }

                }
                else if (real == true && imaginary == false){
                    while (true){
                        A = Random.nextInt(-RangeText, RangeText+1)
                        B = Random.nextInt(-RangeText, RangeText+1)
                        C = Random.nextInt(-RangeText, RangeText+1)
                        if(B*B > 4*A*C && A!=0 && B!=0 && C!=0 ){
                            break
                        }
                    }

                }
                else if (real == false && imaginary == true){
                    while (true){
                        A = Random.nextInt(-RangeText, RangeText+1)
                        B = Random.nextInt(-RangeText, RangeText+1)
                        C = Random.nextInt(-RangeText, RangeText+1)

                        if(B*B < 4*A*C && A!=0 && B!=0 && C!=0){
                            break
                        }
                    }
                }
                rand_str_a.setText(A.toString())
                rand_str_b.setText(B.toString())
                rand_str_c.setText(C.toString())

            }

            var solutionArray: Array<String> = solveEqution(A.toDouble(),B.toDouble(),C.toDouble())
            RandomSol1.setText(solutionArray[0])
            RandomSol2.setText(solutionArray[1])

        }


        buttonChange.setText("Solve")
        buttonChange.setTextColor(ContextCompat.getColor(this, R.color.palette3paste))
        buttonChange.setBackgroundColor(ContextCompat.getColor(this, R.color.palette4green))
        buttonChange.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        RandomClear.setOnClickListener {
            rand_str_a.setText("")
            rand_str_b.setText("")
            rand_str_c.setText("")
            RandomSol1.setText("null")
            RandomSol2.setText("null")
            range.setText("10")
        }
    }
    fun solveEqution(a: Double, b: Double, c: Double ) : Array<String>{
        lateinit var solution : Array<String>
        if(a == 0.0 && b ==0.0 && c == 0.0){
            Toast.makeText(this, "No Input Given", Toast.LENGTH_SHORT).show()
            return arrayOf("", "")
        }
        if (b.pow(2) > 4*a*c || b.pow(2) == 4*a*c){      //no imaginary parts
            solution = arrayOf( ((-b + sqrt(b.pow(2) - 4*a*c))/(2*a*c)).toString(), ((-b - sqrt(b.pow(2) - 4*a*c))/(2*a*c)).toString() )
        }
        else{                           //no real part
            var det = (sqrt((4*a*c - b.pow(2))))/(2*a)  //find square root of the absolute value
            var divBy2a = -b/(2*a)               //rest of the parts of the formula
            solution = arrayOf(String.format("%.4f",divBy2a) + " + i" +String.format("(%.4f)",det), String.format("%.4f",divBy2a) + " - i" +String.format("(%.4f)",det))    //make complex number string
        }

        return solution
    }
}