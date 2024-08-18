package com.example.zooapp2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.zooapp2.data.ZooRepository

class SubMenuActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SELECTION = "extra_selection"
        const val EXTRA_SUBJECT = "extra_subject"
    }

    private lateinit var tvDetailsTitle: TextView
    private lateinit var listViewDetails: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_menu)

        tvDetailsTitle = findViewById(R.id.tvDetailsTitle)
        listViewDetails = findViewById(R.id.listViewDetails)

        // Get strings passed via intent extras
        val selection = intent.getStringExtra(EXTRA_SELECTION) ?: return
        val subject = intent.getStringExtra(EXTRA_SUBJECT) ?: return

        when (subject) {
            "Animal" -> {
                val title = "Please select an available $selection"
                tvDetailsTitle.text = title

                // Get animal data
                val animals = ZooRepository.getAnimals().filter {it.type == selection}
                val animalDetails = animals.map {it.name}

                // Set the list view
                val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, animalDetails) {
                    override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                        val view = super.getView(position, convertView, parent)
                        val animal = animals[position]
                        val textView = view.findViewById<TextView>(android.R.id.text1)
                        var flag = false
                        if (animal.health != "None") {
                            flag = true
                        }
                        if (animal.food == "None") {
                            flag = true
                        }
                        if (flag) {
                            textView.setTextColor(Color.RED)
                            textView.setTypeface(null, android.graphics.Typeface.BOLD)
                        }
                        return view
                    }
                }
                listViewDetails.adapter = adapter

                // Set list view item on click listener
                listViewDetails.setOnItemClickListener { _, _, position, _ ->
                    val selectedAnimalName = animalDetails[position]
                    val selectedAnimal = ZooRepository.getAnimals().find { it.name == selectedAnimalName }
                    selectedAnimal?.let {
                        val intent = Intent(this, AnimalDetailsActivity::class.java)
                        intent.putExtra(AnimalDetailsActivity.EXTRA_ANIMAL_UUID, it.id.toString())
                        startActivity(intent)
                    }
                }
            }
            "Habitat" -> {
                // Set the page title
                val title = "Please select an available $selection"
                tvDetailsTitle.text = title

                // Get habitat data
                val habitats = ZooRepository.getHabitats().filter {it.type == selection}
                val habitatDetails = habitats.map {it.name}

                // Set the list view
                val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, habitatDetails) {
                    override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                        val view = super.getView(position, convertView, parent)
                        val habitat = habitats[position]
                        val textView = view.findViewById<TextView>(android.R.id.text1)
                        var flag = false
                        if (!habitat.cFlag) {
                            flag = true
                        }
                        if (!habitat.fFlag) {
                            flag = true
                        }
                        if (!habitat.tFlag) {
                            flag = true
                        }

                        if (flag) {
                            textView.setTextColor(Color.RED)
                            textView.setTypeface(null, android.graphics.Typeface.BOLD)
                        }
                        return view
                    }
                }
                listViewDetails.adapter = adapter

                // Set list view item on click listener
                listViewDetails.setOnItemClickListener { _, _, position, _ ->
                    val selectedHabitatName = habitatDetails[position]
                    val selectedHabitat = ZooRepository.getHabitats().find { it.name == selectedHabitatName }
                    selectedHabitat?.let {
                        val intent = Intent(this, HabitatDetailsActivity::class.java)
                        intent.putExtra(HabitatDetailsActivity.EXTRA_HABITAT_UUID, it.id.toString())
                        startActivity(intent)
                    }
                }
            }
        }
    }
}