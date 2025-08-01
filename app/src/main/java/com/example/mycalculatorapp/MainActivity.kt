package com.example.mycalculatorapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var textViewInput: TextView? = null
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewInput = findViewById(R.id.textViewInput)
    }



    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = textViewInput?.text.toString()
            var prefix: String = ""

            try {
                if(tvValue.startsWith("-") ){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var firstNum = splitValue[0]
                    var secondNum = splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstNum  = prefix +firstNum
                    }

                    textViewInput?.text = removeZeroAfterDot((firstNum.toDouble() - secondNum.toDouble()).toString())

                } else if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var firstNum = splitValue[0]
                    var secondNum = splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstNum  = prefix +firstNum
                    }

                    textViewInput?.text = removeZeroAfterDot((firstNum.toDouble() + secondNum.toDouble()).toString())

                } else if (tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var firstNum = splitValue[0]
                    var secondNum = splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstNum  = prefix +firstNum
                    }

                    textViewInput?.text = removeZeroAfterDot((firstNum.toDouble() * secondNum.toDouble()).toString())

                } else if (tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var firstNum = splitValue[0]
                    var secondNum = splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstNum  = prefix +firstNum
                    }

                    textViewInput?.text = removeZeroAfterDot((firstNum.toDouble() / secondNum.toDouble()).toString())

                }

            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun removeZeroAfterDot(result:String): String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0,result.length-2)

        return value
    }
    fun onOperator(view: View){
        textViewInput?.text?.let {
            it ->
            if(lastNumeric && !isOperatorAdded(it.toString())){
                textViewInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onDigit(view: View){
        textViewInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false

    }

    fun onClear(view:View){
        textViewInput?.text = null
    }

    fun onDecimalPoint(view: View){

        if(lastNumeric && !lastDot){
            textViewInput?.append(".")
            lastDot = true
            lastNumeric = false
        }

    }

    private fun isOperatorAdded(value:String): Boolean{

        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")||
                    value.contains("+")||
                    value.contains("*")||
                    value.contains("-")

        }
    }
}