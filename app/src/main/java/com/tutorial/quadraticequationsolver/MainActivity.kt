package com.tutorial.quadraticequationsolver

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.math.pow
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val str_a: EditText = findViewById(R.id.a)
        val str_b: EditText = findViewById(R.id.b)
        val str_c: EditText = findViewById(R.id.c)
        val zero: EditText = findViewById(R.id.zero)
        zero.setInputType(InputType.TYPE_NULL);
        zero.isFocusable = false
        zero.isFocusableInTouchMode = false
        val sol1: TextView = findViewById(R.id.sol1)
        val sol2: TextView = findViewById(R.id.sol2)
        val solve: Button = findViewById(R.id.solve)
        val clear: Button = findViewById(R.id.clear)
        val buttonChange: Button = findViewById(R.id.buttonChange)
        buttonChange.setText("Generate")
        buttonChange.setTextColor(ContextCompat.getColor(this, R.color.palette2Skin))
        buttonChange.setBackgroundColor(ContextCompat.getColor(this, R.color.palette4orange))

        buttonChange.setOnClickListener {
            val intent = Intent(this, RandomEquation::class.java)
            startActivity(intent)
            finish()

        }



        val onTouchListener: View.OnTouchListener = View.OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val editText = view as EditText
                if (editText.text.toString() == "0") {
                    editText.text.clear()
                }
            }
            false // Return false to allow the event to continue processing
        }

        str_a.setOnTouchListener(onTouchListener)
        str_b.setOnTouchListener(onTouchListener)
        str_c.setOnTouchListener(onTouchListener)

        solve.setOnClickListener {

            var a = str_a.getText().toString().toDouble()
            var b = str_b.getText().toString().toDouble()
            var c = str_c.getText().toString().toDouble()
            var solutionArray: Array<String> = solveEqution(a,b,c)

            sol1.setText(solutionArray[0])
            sol2.setText(solutionArray[1])

        }

        clear.setOnClickListener {
            str_a.setText("0")
            str_b.setText("0")
            str_c.setText("0")
            sol1.setText("")
            sol2.setText("")
        }



    }

    fun solveEqution(a: Double, b: Double, c: Double ) : Array<String>{
        lateinit var solution : Array<String>
        if(a == 0.0 || b ==0.0 || c == 0.0){
            Toast.makeText(this, "Enter non-zero values", Toast.LENGTH_SHORT).show()
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