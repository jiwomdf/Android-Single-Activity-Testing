package com.programmergabut.android_jetpack_testing.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmergabut.android_jetpack_testing.data.local.ShoppingItem
import com.programmergabut.android_jetpack_testing.data.remote.responses.ImageResponse
import com.programmergabut.android_jetpack_testing.other.Constants
import com.programmergabut.android_jetpack_testing.other.Event
import com.programmergabut.android_jetpack_testing.other.Resource
import com.programmergabut.android_jetpack_testing.repositories.ShoppingRepository
import kotlinx.coroutines.launch
import kotlin.Exception

class ShoppingViewModel @ViewModelInject constructor(
    private val repository: ShoppingRepository
): ViewModel() {

    val shoppingItems = repository.observeAllShoppingItem()

    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus

    fun setCurImageUrl(url: String){
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDB(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amountStr: String, priceStr: String){
        if(name.isEmpty() || amountStr.isEmpty() || priceStr.isEmpty()){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The field must not be empty", null)))
            return
        }
        if(name.length > Constants.MAX_NAME_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The name of the item must not exceed " +
                    "${Constants.MAX_NAME_LENGTH} characters", null)))
            return
        }
        if(priceStr.length > Constants.MAX_PRICE_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The price of the item must not exceed " +
                    "${Constants.MAX_PRICE_LENGTH} characters", null)))
            return
        }
        val amount = try {
            amountStr.toInt()
        }catch (e: Exception){
            _insertShoppingItemStatus.postValue(Event(Resource.error("please insert a valid amount", null)))
            return
        }

        val shoppingItem = ShoppingItem(name, amount, priceStr.toFloat(), _curImageUrl.value ?: "")
        insertShoppingItemIntoDB(shoppingItem)
        setCurImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }

    fun searchForImage(imageQuery: String){
        if(imageQuery.isEmpty()){
            return
        }
        _images.value = Event(Resource.loading(null))

        viewModelScope.launch {
            val response = repository.searchForImage(imageQuery)
            _images.value = Event(response)
        }
    }



}