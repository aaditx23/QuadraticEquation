package com.tutorial.quadraticequationsolver


import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.math.pow
import kotlin.math.sqrt


class SolveFragment : Fragment() {
    lateinit var str_a: EditText
    lateinit var str_b: EditText
    lateinit var str_c: EditText
    lateinit var zero: EditText
    lateinit var sol1: TextView
    lateinit var sol2: TextView
    lateinit var solve: Button
    lateinit var clear: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onTouchListener: View.OnTouchListener = View.OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val editText = view as EditText
                if (editText.text.toString() == "0") {
                    editText.text.clear()
                }
            }
            false // Return false to allow the event to continue processing
        }

        str_a = view.findViewById(R.id.a)
        str_b = view.findViewById(R.id.b)
        str_c = view.findViewById(R.id.c)
        zero = view.findViewById(R.id.zero)
        zero.setInputType(InputType.TYPE_NULL);
        zero.isFocusable = false
        zero.isFocusableInTouchMode = false
        sol1 = view.findViewById(R.id.sol1)
        sol2 = view.findViewById(R.id.sol2)
        solve = view.findViewById(R.id.solve)
        clear = view.findViewById(R.id.clear)

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_solve, container, false)
    }

    fun solveEqution(a: Double, b: Double, c: Double ) : Array<String>{
        lateinit var solution : Array<String>
        if(a == 0.0 || b ==0.0 || c == 0.0){
            //Toast.makeText(, "Enter non-zero values", Toast.LENGTH_SHORT).show()
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