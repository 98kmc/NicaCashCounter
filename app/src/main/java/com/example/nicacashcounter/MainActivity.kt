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

    companion object{
        //et050 is a global variable cause need to referenciate out of OnCreate metod


        private lateinit var mainContext: Context

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainContext = this

        var btnClean = findViewById<Button>(R.id.btnClean)
        var llTotal = findViewById<LinearLayout>(R.id.llTotal)
        llTotal.isEnabled = false
        var etGrandTotal = findViewById<EditText>(R.id.etGranTotal)

        var et050 = findViewById<EditText>(R.id.et050)
        var etUno = findViewById<EditText>(R.id.et1)
        var etCinco = findViewById<EditText>(R.id.et5)
        var etDiez = findViewById<EditText>(R.id.et10)
        var etVeinte = findViewById<EditText>(R.id.et20)
        var etCincuenta = findViewById<EditText>(R.id.et50)
        var etCien = findViewById<EditText>(R.id.et100)
        var etDoscientos = findViewById<EditText>(R.id.et200)
        var etQuinientos = findViewById<EditText>(R.id.et500)
        var etMil = findViewById<EditText>(R.id.et1000)
        var tvTotal050 = findViewById<TextView>(R.id.tvtotal050)
        var tvTotal1 = findViewById<TextView>(R.id.tvtotal1)
        var tvTotal5 = findViewById<TextView>(R.id.tvtotal5)
        var tvTotal10 = findViewById<TextView>(R.id.tvtotal10)
        var tvTotal20 = findViewById<TextView>(R.id.tvtotal20)
        var tvTotal50 = findViewById<TextView>(R.id.tvtotal50)
        var tvTotal100 = findViewById<TextView>(R.id.tvtotal100)
        var tvTotal200 = findViewById<TextView>(R.id.tvtotal200)
        var tvTotal500 = findViewById<TextView>(R.id.tvtotal500)
        var tvTotal1000 = findViewById<TextView>(R.id.tvtotal1000)


        //TextView array to control the clean metod of Total Textviews
        var arrayTotales = arrayOf(
            tvTotal050,
            tvTotal1,
            tvTotal5,
            tvTotal10,
            tvTotal20,
            tvTotal50,
            tvTotal100,
            tvTotal200,
            tvTotal500,
            tvTotal1000
        )
        //EditText array to control the clean metod
        var arrayEts = arrayOf(
            et050,
            etUno,
            etCinco,
            etDiez,
            etVeinte,
            etCincuenta,
            etCien,
            etDoscientos,
            etQuinientos,
            etMil
        )


        // fun to calculate Grand Total in Float format
        fun setGrandTotal() {
            var suma = 0f
            for (it in arrayTotales) {
                if (!it.text.isNullOrEmpty() && it.text != "Total") {
                    suma += it.text.toString().toFloat()
                    etGrandTotal.setText(suma.toString())
                }
            }
            if (suma == 0f) etGrandTotal.text.clear()
        }

        btnClean.setOnClickListener {
           for (it in arrayEts) {it.text.clear();it.clearFocus()}
            etGrandTotal.text.clear()
            hide_keypad(it)
            etGrandTotal.clearFocus()
            llTotal.isEnabled = false
        }

        etGrandTotal.setOnTouchListener { view, _ ->
            hide_keypad(view)
            true
        }

        etGrandTotal.setOnFocusChangeListener { view, _ ->
            hide_keypad(view)
            switchGranTotal(llTotal)
        }

        et050.addTextChangedListener {
            setTotal(et050, et050, tvTotal050, "50_centavos")
            setGrandTotal()
        }

        etUno.addTextChangedListener {
            setTotal(et050, etUno, tvTotal1, "1_cordoba")
            setGrandTotal()
        }

        etCinco.addTextChangedListener {
            setTotal(et050, etCinco, tvTotal5, "5_cordobas")
            setGrandTotal()
        }

        etDiez.addTextChangedListener {
            setTotal(et050, etDiez, tvTotal10, "10_cordobas")
            setGrandTotal()
        }

        etVeinte.addTextChangedListener {
            setTotal(et050, etVeinte, tvTotal20, "20_cordobas")
            setGrandTotal()
        }

        etCincuenta.addTextChangedListener {
            setTotal(et050, etCincuenta, tvTotal50, "50_cordobas")
            setGrandTotal()
        }

        etCien.addTextChangedListener {
            setTotal(et050, etCien, tvTotal100, "100_cordobas")
            setGrandTotal()
        }

        etDoscientos.addTextChangedListener {
            setTotal(et050, etDoscientos, tvTotal200, "200_cordobas")
            setGrandTotal()
        }
        etQuinientos.addTextChangedListener {
            setTotal(et050, etQuinientos, tvTotal500, "500_cordobas")
            setGrandTotal()
        }

        etMil.addTextChangedListener {
            setTotal(et050, etMil, tvTotal1000, "1000_cordobas")
            setGrandTotal()
        }
    }

    private fun toMultiply(amount:Float, value:String): Float = amount * values.get(value)!!

    private fun setTotal(compare: EditText,et:EditText,tv:TextView, value:String){
        if (et.text.isNullOrEmpty()){tv.text = "Total"}
        else{
            if (et == compare) tv.text = toMultiply(et.text.toString().toFloat(), value).toString()
            else tv.text = toMultiply(et.text.toString().toFloat(), value).roundToInt().toString()

        }
    }

    private fun switchGranTotal(ll: LinearLayout)
    {
        if (ll.isEnabled) ll.isEnabled=false
        else {
            if (!ll.isEnabled) ll.isEnabled= true
        }
    }
    private fun hide_keypad(v:View){
        val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken,0)
    }


}