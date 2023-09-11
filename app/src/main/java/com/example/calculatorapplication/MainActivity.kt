package com.example.calculatorapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.text.isDigitsOnly
import com.example.calculatorapplication.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var stateError=false
    var lastNum=false
    var lastpoint=false
    private lateinit var expression:Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun OnClearClick(view: View) {
        binding.DataTv.text=""
        lastNum=false
    }
    fun OnOperationClick(view: View) {
        if(!stateError && lastNum){
            binding.DataTv.append((view as Button).text)
            stateError=false
            lastNum=false
            onequal()
        }
    }
    fun OnDigitClick(view: View) {
        if(stateError){
            binding.DataTv.text=(view as Button).text
            stateError=false
        }
        else{
            binding.DataTv.append((view as Button).text)
        }
        lastNum=true
        onequal()
    }
    fun OnAllClearClick(view: View) {
        binding.ResultTv.text=""
        binding.DataTv.text=""
        stateError=false
        lastNum=false
        lastpoint=false
        binding.ResultTv.visibility=View.GONE
    }
    fun OnEqualClick(view: View) {
        onequal()
        binding.DataTv.text=binding.ResultTv.text.toString()
    }

    fun onequal(){
        if(lastNum && !stateError){
            val text=binding.DataTv.text.toString()
            expression=ExpressionBuilder(text).build()
        }
        try {
            val result=expression.evaluate()
            binding.ResultTv.visibility=View.VISIBLE
            binding.ResultTv.text="=" + result.toString()
        }
        catch (ex: ArithmeticException){
            Log.e("evaluate Error",ex.toString() )
            binding.ResultTv.text="Error 404"
            stateError=true
            lastNum=false
        }

    }

    fun OnBackClick(view: View) {
        binding.DataTv.text=binding.DataTv.text.toString().dropLast(1)
        try {
            val lastChar=binding.DataTv.text.toString().dropLast(1)
            if(lastChar.isDigitsOnly()){
                onequal()
            }
        }
        catch (e :Exception){
            binding.ResultTv.text=""
            binding.ResultTv.visibility=View.GONE
            Log.e("the last char error ",e.toString())
        }
    }
}

