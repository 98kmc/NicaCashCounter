package com.example.nicacashcounter

import android.annotation.SuppressLint
import android.app.UiModeManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.widget.addTextChangedListener
import com.example.nicacashcounter.databinding.ActivityMainBinding
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    var values =mapOf(
        "50_centavos" to 0.50f,
        "1_cordoba" to 1f,
        "5_cordobas" to 5f,
        "10_cordobas" to 10f,
        "20_cordobas" to 20f,
        "50_cordobas" to 50f,
        "100_cordobas" to 100f,
        "200_cordobas" to 200f,
        "500_cordobas" to 500f,
        "1000_cordobas" to 1000f,
    )

    private lateinit var main: ActivityMainBinding
    companion object{
        //et050 is a global variable cause need to referenciate out of OnCreate metod
        @SuppressLint("StaticFieldLeak")
        private lateinit var mainContext: Context

    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mainContext = this

        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        //........

        //........

        main.llTotal.isEnabled = false
        //........

        //........

        //TextView array to control the clean metod of Total Textviews
        var arrayTotales = arrayOf(
            main.tvtotal050,
            main.tvtotal1,
            main.tvtotal5,
            main.tvtotal10,
            main.tvtotal20,
            main.tvtotal50,
            main.tvtotal100,
            main.tvtotal200,
            main.tvtotal500,
            main.tvtotal1000
        )
        //EditText array to control the clean metod
        var arrayEts = arrayOf(
            main.et050,
            main.et1,
            main.et5,
            main.et10,
            main.et20,
            main.et50,
            main.et100,
            main.et200,
            main.et500,
            main.et1000
        )


        // fun to calculate Grand Total in Float format
        fun setGrandTotal() {
            var suma = 0f
            arrayTotales.map {
                if (!it.text.isNullOrEmpty() && it.text != "Total") {
                    suma += it.text.toString().toFloat()
                    main.etGranTotal.setText(suma.toString())
                }
                if (suma == 0f) main.etGranTotal.text.clear()
            }

            /*
            for (it in arrayTotales) {
                if (!it.text.isNullOrEmpty() && it.text != "Total") {
                    suma += it.text.toString().toFloat()
                    main.etGranTotal.setText(suma.toString())
                }
            }
            if (suma == 0f) main.etGranTotal.text.clear()*/
        }

        main.btnClean.setOnClickListener {
            arrayEts.map { et ->
                et.text.clear()
                et.clearFocus()
            }
            main.etGranTotal.text.clear()
            hide_keypad(it)
            main.etGranTotal.clearFocus()
            main.llTotal.isEnabled = false

            /*
           for (it in arrayEts) {it.text.clear();it.clearFocus()}
            etGrandTotal.text.clear()
            hide_keypad(it)
            etGrandTotal.clearFocus()
            llTotal.isEnabled = false*/
        }

        main.etGranTotal.setOnTouchListener { view, _ ->
            hide_keypad(view)
            true
        }

        main.etGranTotal.setOnFocusChangeListener { view, _ ->
            hide_keypad(view)
            switchGranTotal(main.llTotal)
        }

        main.et050.addTextChangedListener {
            setTotal(main.et050, main.et050, main.tvtotal050, "50_centavos")
            setGrandTotal()
        }

        main.et1.addTextChangedListener {
            setTotal(main.et050, main.et1, main.tvtotal1, "1_cordoba")
            setGrandTotal()
        }

        main.et5.addTextChangedListener {
            setTotal(main.et050, main.et5, main.tvtotal5, "5_cordobas")
            setGrandTotal()
        }

        main.et10.addTextChangedListener {
            setTotal(main.et050, main.et10, main.tvtotal10, "10_cordobas")
            setGrandTotal()
        }

        main.et20.addTextChangedListener {
            setTotal(main.et050, main.et20, main.tvtotal20, "20_cordobas")
            setGrandTotal()
        }

        main.et50.addTextChangedListener {
            setTotal(main.et050, main.et50, main.tvtotal50, "50_cordobas")
            setGrandTotal()
        }

        main.et100.addTextChangedListener {
            setTotal(main.et050, main.et100, main.tvtotal100, "100_cordobas")
            setGrandTotal()
        }

        main.et200.addTextChangedListener {
            setTotal(main.et050, main.et200, main.tvtotal200, "200_cordobas")
            setGrandTotal()
        }
        main.et500.addTextChangedListener {
            setTotal(main.et050, main.et500, main.tvtotal500, "500_cordobas")
            setGrandTotal()
        }

        main.et1000.addTextChangedListener {
            setTotal(main.et050, main.et1000, main.tvtotal1000, "1000_cordobas")
            setGrandTotal()
        }
    }

    private fun toMultiply(amount:Float, value:String): Float = amount * values[value]!!

    private fun setTotal(compare: EditText,et:EditText,tv:TextView, value:String){
        if (!et.text.isNullOrEmpty()) {
            if (et == compare) tv.text = toMultiply(et.text.toString().toFloat(), value).toString()
            else tv.text = toMultiply(et.text.toString().toFloat(), value).roundToInt().toString()
        } else {tv.text = "Total"}
    }

    private fun switchGranTotal(ll: LinearLayout)
    {
        when {
            ll.isEnabled -> ll.isEnabled=false
            else -> if (!ll.isEnabled) ll.isEnabled= true
        }
    }
    private fun hide_keypad(v:View){
        val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken,0)
    }


}