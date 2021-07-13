package com.programmergabut.singleactivitytesting.repositories

import androidx.lifecycle.LiveData
import com.programmergabut.singleactivitytesting.data.local.ShoppingItem
import com.programmergabut.singleactivitytesting.data.remote.responses.ImageResponse
import com.programmergabut.singleactivitytesting.other.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>

}