package com.example.geometry
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val shapeInputLayout = findViewById<LinearLayout>(R.id.shape_input_layout)
        val radiusEditText = findViewById<EditText>(R.id.radius_edittext)
        val sideEditText = findViewById<EditText>(R.id.side_edittext)
        val baseEditText = findViewById<EditText>(R.id.baseEditText)
        val heightEditText = findViewById<EditText>(R.id.heightEditText)
        val base_height_layout = findViewById<LinearLayout>(R.id.base_height_layout)

        val spinner: Spinner = findViewById(R.id.shape_spinner)
        val options = resources.getStringArray(R.array.spinner_options)
        val shapeImage = findViewById<ImageView>(R.id.shape_image)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedShape = options[position]

                when (selectedShape) {
                    "Circulo" -> {
                        shapeInputLayout.visibility = View.VISIBLE
                        radiusEditText.visibility = View.VISIBLE
                        sideEditText.visibility = View.GONE
                        base_height_layout.visibility = View.GONE
                    }
                    "Cuadrado" -> {
                        shapeInputLayout.visibility = View.VISIBLE
                        radiusEditText.visibility = View.GONE
                        sideEditText.visibility = View.VISIBLE
                        base_height_layout.visibility = View.GONE
                    }
                    "Rectangulo", "Triangulo" -> {
                        shapeInputLayout.visibility = View.VISIBLE
                        radiusEditText.visibility = View.GONE
                        sideEditText.visibility = View.GONE
                        base_height_layout.visibility = View.VISIBLE
                    }
                }

                val resourceId = when (selectedShape) {
                    "Circulo" -> R.drawable.circle_shape
                    "Rectangulo" -> R.drawable.rectangle_shape
                    "Cuadrado" -> R.drawable.square_shape
                    "Triangulo" -> R.drawable.triangle_shape
                    else -> 0
                }
                shapeImage.setImageResource(resourceId)
                shapeImage.contentDescription = selectedShape
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val calculateAreaButton: Button = findViewById(R.id.calculate_area_button)
        calculateAreaButton.setOnClickListener {
            val selectedShape = spinner.selectedItem.toString()
            val area = when (selectedShape){
                "Circulo" -> calculateCircleArea(radiusEditText.text.toString().toDouble())
                "Rectangulo" -> calculateRectangleArea(baseEditText.text.toString().toDouble(), heightEditText.text.toString().toDouble())
                "Cuadrado" -> calculateSquareArea(sideEditText.text.toString().toDouble())
                "Triangulo" -> calculateTriangleArea(baseEditText.text.toString().toDouble(), heightEditText.text.toString().toDouble())
                else -> 0.0
            }
            val intent = Intent(this, result_activity::class.java)
            intent.putExtra("AREA", area)
            startActivity(intent)
        }
    }

    private fun calculateCircleArea(radio: Double): Double{
        return Math.PI * (radio * radio)
    }

    private fun calculateRectangleArea(b: Double, h: Double): Double{
        return b * h
    }

    private fun calculateTriangleArea(b: Double, h: Double): Double{
        return (b / 2.0) * h
    }

    private fun calculateSquareArea(l: Double): Double{
        return l * l
    }
}
