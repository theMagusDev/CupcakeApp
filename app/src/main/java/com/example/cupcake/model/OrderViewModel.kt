package com.example.cupcake.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.cupcake.R
import com.example.cupcake.data.Datasource
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

// Price for a single cupcake
private const val PRICE_PER_CUPCAKE = 2.00

// Additional cost for same day pickup of an order
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {
    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    private var _enabledPickupOptions = listOf<Boolean>()
    val enabledPickupOptions: List<Boolean>
        get() = _enabledPickupOptions

    val bananaFlavorPickupDates = Datasource().loadStorageOf("Banana")
    val dateOptions = getPickupOptions()

    init {
        resetOrder()
    }

    fun setQuantity(numberOfCupcakes: Int) {
        _quantity.value = numberOfCupcakes
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    fun hasNoPickupDateSet(): Boolean {
        return _date.value.isNullOrEmpty()
    }

    fun findFirstAvailableOptionIndex(): Int? {
        for (i in _enabledPickupOptions.indices) {
            if (_enabledPickupOptions[i]) return i
        }

        return null
    }

    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        // Create a list of dates starting with the current date and the following 3 dates
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }

        return options
    }

    private fun updatePrice() {
        var calculatedPrice = (_quantity.value ?: 0) * PRICE_PER_CUPCAKE

        // If the user selected the first option (today) for pickup, add the surcharge
        if (_date.value == dateOptions[0])
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP

        _price.value = calculatedPrice
    }

    fun updateEnabledPickupOptions() {
        val finalList = mutableListOf<Boolean>()
        when(_flavor.value) {
            "Banana" -> {
                finalList.addAll(bananaFlavorPickupDates)
            }
            else -> {
                repeat(dateOptions.size) {
                    finalList.add(true)
                }
            }
        }
        _enabledPickupOptions = finalList
    }

    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = ""
        _price.value = 0.0
    }
}