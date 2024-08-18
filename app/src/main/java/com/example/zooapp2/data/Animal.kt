package com.example.zooapp2.data

import java.util.UUID

data class Animal(
    var id: UUID,
    var type: String,
    var name: String,
    var age: Int,
    var health: String,
    var food: String
)

