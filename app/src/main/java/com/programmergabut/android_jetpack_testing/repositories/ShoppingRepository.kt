package com.programmergabut.android_jetpack_testing.repositories

import androidx.lifecycle.LiveData
import com.programmergabut.android_jetpack_testing.data.local.ShoppingItem
import com.programmergabut.android_jetpack_testing.data.remote.responses.ImageResponse
import com.programmergabut.android_jetpack_testing.other.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>

}