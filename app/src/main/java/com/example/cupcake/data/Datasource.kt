package com.example.cupcake.data

import android.util.Log

class Datasource {
    fun loadStorageOf(flavor: String): List<Boolean> {
        val storage = mutableListOf<Boolean>()

        when(flavor) {
            "Banana" -> {
                while((calculateSelectableOptions(storage) == 0) or (calculateSelectableOptions(storage) > 3)) {
                    storage.clear()
                    repeat(4) {
                        storage.add(kotlin.random.Random.nextBoolean())
                    }
                }
            }
            else -> {
                repeat(4) {
                    storage.add(true)
                }
            }
        }
        Log.d("Datasource", storage.toString())
        return storage
    }

    private fun calculateSelectableOptions(listOptions: List<Boolean>): Int {
        var counter = 0

        for (element in listOptions) {
            if (element) ++counter
        }

        return counter
    }
}
