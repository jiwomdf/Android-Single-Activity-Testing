package com.programmergabut.android_jetpack_testing.repositories

import androidx.lifecycle.LiveData
import com.programmergabut.android_jetpack_testing.data.local.ShoppingDao
import com.programmergabut.android_jetpack_testing.data.local.ShoppingItem
import com.programmergabut.android_jetpack_testing.data.remote.PixabayAPI
import com.programmergabut.android_jetpack_testing.data.remote.responses.ImageResponse
import com.programmergabut.android_jetpack_testing.other.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
):  ShoppingRepository{

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)

            if(response.isSuccessful){
                if(response.body() != null)
                    Resource.success(response.body())
                else
                    Resource.error("unknown error occured", null)
            }
            else{
                Resource.error("unknown error occured", null)
            }
        }
        catch (ex: Exception){
            Resource.error(ex.message.toString(), null)
        }
    }


}