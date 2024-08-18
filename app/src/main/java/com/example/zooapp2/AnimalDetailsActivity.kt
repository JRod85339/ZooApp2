package com.example.zooapp2

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zooapp2.data.Animal
import com.example.zooapp2.data.ZooRepository
import java.util.UUID

class AnimalDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ANIMAL_UUID = "extra_animal_uuid"
    }

    private lateinit var tvTitle: TextView
    private lateinit var tvId: TextView
    private lateinit var etType: EditText
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var lineH: LinearLayout
    private lateinit var etHealth: EditText
    private lateinit var lineF: LinearLayout
    private lateinit var etFood: EditText
    private lateinit var btnEditSubmit: Button
    private lateinit var btnDelete: Button

    private var animal: Animal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_details)

        // Initialize views
        tvTitle = findViewById(R.id.tvTitle)
        tvId = findViewById(R.id.tvId)
        etType = findViewById(R.id.etType)
        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        lineH = findViewById(R.id.healthConcernLine)
        etHealth = findViewById(R.id.etHealth)
        lineF = findViewById(R.id.feedingScheduleLine)
        etFood = findViewById(R.id.etFood)
        btnEditSubmit = findViewById(R.id.btnEditSubmit)
        btnDelete = findViewById(R.id.btnDelete)

        // Set title
        tvTitle.text = getString(R.string.new_animal_title)

        // Check if animal UUID is passed via intent extras
        val animalUUIDString = intent.getStringExtra(EXTRA_ANIMAL_UUID)
        if (animalUUIDString != null) {
            val animalUUID = UUID.fromString(animalUUIDString)
            animal = ZooRepository.getAnimal(animalUUID)
        }

        // Populate UI with animal data if available
        animal?.let {
            val title = it.name + " the " + it.type
            tvTitle.text = title
            val id = "ID: " + it.id
            tvId.text = id
            etType.setText(it.type)
            etName.setText(it.name)
            etAge.setText(it.age.toString())
            etHealth.setText(it.health)
            etFood.setText(it.food)
            //Highlight lines if warning condition is met
            if (it.health != "None") {
                lineH.setBackgroundColor(Color.rgb(236,151,151))
            }
            if (it.food == "None") {
                lineF.setBackgroundColor(Color.rgb(236,151,151))
            }
            // Enable the delete button if an animal is present
            btnDelete.isEnabled = true
        }

        // Set click listeners
        btnEditSubmit.setOnClickListener {
            if(checkBlankEditTexts(etType, etName, etAge, etHealth, etFood)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else  {
                if (animal != null) {
                    updateAnimal()
                } else {
                    saveAnimal()
                }
            }
        }

        var clickCount = 0
        btnDelete.setOnClickListener {
            clickCount++
            when(clickCount) {
                1 -> {
                    btnDelete.text = getString(R.string.confirm_delete)
                    btnDelete.setBackgroundColor(Color.RED)
                }
                2 -> {
                    deleteAnimal()
                }
                else -> {
                    Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkBlankEditTexts(vararg editTexts: EditText): Boolean {
        for (editText in editTexts) {
            if (editText.text.isBlank()) {
                return true
            }
        }
        return false
    }
    private fun updateAnimal() {
        animal?.let {
            it.type = etType.text.toString().trim()
            it.name = etName.text.toString().trim()
            it.age = etAge.text.toString().toIntOrNull() ?: 0
            it.health = etHealth.text.toString().trim()
            it.food = etFood.text.toString().trim()
            ZooRepository.updateAnimal(it)
        }
        finish()
    }

    private fun saveAnimal() {
        val type = etType.text.toString().trim()
        val name = etName.text.toString().trim()
        val age = etAge.text.toString().toIntOrNull() ?: 0
        val health = etHealth.text.toString().trim()
        val food = etFood.text.toString().trim()
        val newAnimal = Animal(UUID.randomUUID(), type, name, age, health, food)
        ZooRepository.addAnimal(newAnimal)
        finish()
    }

    private fun deleteAnimal() {
        animal?.let {
            ZooRepository.deleteAnimal(it.id)
        }
        finish()
    }
}