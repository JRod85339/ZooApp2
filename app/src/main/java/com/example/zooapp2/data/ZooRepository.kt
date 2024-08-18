package com.example.zooapp2.data

import java.util.UUID

object ZooRepository {
    private val animalsMap: MutableMap<UUID, Animal> = mutableMapOf()
    private val habitatsMap: MutableMap<UUID, Habitat> = mutableMapOf()

    init {
        // Sample data initialization
        addAnimal(Animal(UUID.randomUUID(), "Lion", "Leo", 5, "Cut on left front paw", "2x daily"))
        addAnimal(Animal(UUID.randomUUID(), "Tiger", "Maj", 15, "None", "3x daily"))
        addAnimal(Animal(UUID.randomUUID(), "Bear", "Baloo", 1, "None", "None"))
        addAnimal(Animal(UUID.randomUUID(), "Giraffe", "Spots", 12, "None", "Grazing"))
        addAnimal(Animal(UUID.randomUUID(), "Lion", "Jeff", 4, "None", "None"))

        // Mock Habitats
        addHabitat(Habitat(UUID.randomUUID(), "Tundra", "Penguin", "-30 - 35 F", false, "Fish running low", false, "Passed", true))
        addHabitat(Habitat(UUID.randomUUID(), "Tundra", "Polar Bear", "-30 - 35 F", true, "Fish and Beef", true, "Passed", true))
        addHabitat(Habitat(UUID.randomUUID(), "Rainforest", "Apiary", "68 - 84 F", true, "Seeds and worms", true, "Passed", true))
        addHabitat(Habitat(UUID.randomUUID(), "Rainforest", "Boa", "68 - 84 F", true, "Mice", true, "Passed", true))
        addHabitat(Habitat(UUID.randomUUID(), "Marine", "Coral Reef", "68 - 97 F", false, "Plankton", true, "Passed", true))
        addHabitat(Habitat(UUID.randomUUID(), "Marine", "Jellyfish", "60 - 78 F", true, "Plankton", true, "Needs cleaning from algae", false))
    }

    fun getAnimals(): List<Animal> {
        return animalsMap.values.toList()
    }

    fun getAnimal(id: UUID): Animal? {
        return animalsMap[id]
    }

    fun addAnimal(animal: Animal) {
        animalsMap[animal.id] = animal
    }

    fun updateAnimal(animal: Animal) {
        animalsMap[animal.id]?.let {
            animalsMap[animal.id] = animal
        }
    }

    fun deleteAnimal(id: UUID) {
        animalsMap.remove(id)
    }

    fun getHabitats(): List<Habitat> {
        return habitatsMap.values.toList()
    }

    fun getHabitat(id: UUID): Habitat? {
        return habitatsMap[id]
    }

    fun addHabitat(habitat: Habitat) {
        habitatsMap[habitat.id] = habitat
    }

    fun updateHabitat(habitat: Habitat) {
        habitatsMap[habitat.id]?.let {
            habitatsMap[habitat.id] = habitat
        }
    }

    fun deleteHabitat(id: UUID) {
        habitatsMap.remove(id)
    }
}
