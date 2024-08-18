package com.example.zooapp2.data

import java.util.UUID

data class Habitat(
    var id: UUID,
    var type: String,
    var name: String,
    var temp: String,
    var tFlag: Boolean,
    var food: String,
    var fFlag: Boolean,
    var clean: String,
    var cFlag: Boolean
)