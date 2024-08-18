package com.example.zooapp2

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zooapp2.data.Habitat
import com.example.zooapp2.data.ZooRepository
import java.util.UUID

class HabitatDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_HABITAT_UUID = "extra_habitat_uuid"
    }

    private lateinit var tvTitle: TextView
    private lateinit var tvId: TextView
    private lateinit var etType: EditText
    private lateinit var etName: EditText
    private lateinit var lineT: LinearLayout
    private lateinit var etTemp: EditText
    private lateinit var cbTFlag: CheckBox
    private lateinit var lineF: LinearLayout
    private lateinit var etFood: EditText
    private lateinit var cbFFlag: CheckBox
    private lateinit var lineC: LinearLayout
    private lateinit var etClean: EditText
    private lateinit var cbCFlag: CheckBox
    private lateinit var btnEditSubmit: Button
    private lateinit var btnDelete: Button

    private var habitat: Habitat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habitat_details)

        // Initialize views
        tvTitle = findViewById(R.id.tvTitle)
        tvId = findViewById(R.id.tvId)
        etType = findViewById(R.id.etType)
        etName = findViewById(R.id.etName)
        // Temperature
        lineT = findViewById(R.id.temperatureLine)
        etTemp = findViewById(R.id.etTemp)
        cbTFlag = findViewById(R.id.cbTFlag)
        // Food Supply
        lineF = findViewById(R.id.foodSupplyLine)
        etFood = findViewById(R.id.etFood)
        cbFFlag = findViewById(R.id.cbFFlag)
        // Cleanliness
        lineC = findViewById(R.id.cleanlinessLine)
        etClean = findViewById(R.id.etClean)
        cbCFlag = findViewById(R.id.cbCFlag)
        btnEditSubmit = findViewById(R.id.btnEditSubmit)
        btnDelete = findViewById(R.id.btnDelete)

        // Set title
        tvTitle.text = getString(R.string.new_habitat_title)

        // Check if habitat UUID is passed via intent extras
        val habitatUUIDString = intent.getStringExtra(EXTRA_HABITAT_UUID)
        if (habitatUUIDString != null) {
            val habitatUUID = UUID.fromString(habitatUUIDString)
            habitat = ZooRepository.getHabitat(habitatUUID)
        }

        // Populate UI with habitat data if available
        habitat?.let {
            val title = it.name + " Habitat Details"
            val id = "ID: " + it.id
            tvTitle.text = title
            tvId.text = id
            etType.setText(it.type)
            etName.setText(it.name)
            etTemp.setText(it.temp)
            cbTFlag.isChecked = it.tFlag
            etFood.setText(it.food)
            cbFFlag.isChecked = it.fFlag
            etClean.setText(it.clean)
            cbCFlag.isChecked = it.cFlag
            //Highlight lines if box is not checked
            if (!it.tFlag) {
                lineT.setBackgroundColor(Color.rgb(236,151,151))
            }
            if (!it.fFlag) {
                lineF.setBackgroundColor(Color.rgb(236,151,151))
            }
            if (!it.cFlag) {
                lineC.setBackgroundColor(Color.rgb(236,151,151))
            }

            // Enable the delete button if a habitat is present
            btnDelete.isEnabled = true
        }

        // Set click listeners
        btnEditSubmit.setOnClickListener {
            if(checkBlankEditTexts(etType, etName, etTemp, etFood, etClean)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else  {
                if (habitat != null) {
                    updateHabitat()
                } else {
                    saveHabitat()
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
                    deleteHabitat()
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

    private fun updateHabitat() {
        habitat?.let {
            it.type = etType.text.toString().trim()
            it.name = etName.text.toString().trim()
            it.temp = etTemp.text.toString().trim()
            it.tFlag = cbTFlag.isChecked
            it.food = etFood.text.toString().trim()
            it.fFlag = cbFFlag.isChecked
            it.clean = etClean.text.toString().trim()
            it.cFlag = cbCFlag.isChecked
            ZooRepository.updateHabitat(it)
        }
        finish()
    }

    private fun saveHabitat() {
        val type = etType.text.toString().trim()
        val name = etName.text.toString().trim()
        val temp = etTemp.text.toString().trim()
        val tFlag = cbTFlag.isChecked
        val food = etFood.text.toString().trim()
        val fFlag = cbFFlag.isChecked
        val clean = etClean.text.toString().trim()
        val cFlag = cbCFlag.isChecked
        val newHabitat = Habitat(UUID.randomUUID(), type, name, temp, tFlag, food, fFlag, clean, cFlag)
        ZooRepository.addHabitat(newHabitat)
        finish()
    }

    private fun deleteHabitat() {
        habitat?.let {
            ZooRepository.deleteHabitat(it.id)
        }
        finish()
    }
}