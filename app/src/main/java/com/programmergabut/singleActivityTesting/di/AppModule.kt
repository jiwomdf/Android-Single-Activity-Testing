package com.programmergabut.singleActivityTesting.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.programmergabut.singleActivityTesting.R
import com.programmergabut.singleActivityTesting.data.local.ShoppingDao
import com.programmergabut.singleActivityTesting.data.local.ShoppingItemDatabase
import com.programmergabut.singleActivityTesting.data.remote.PixabayAPI
import com.programmergabut.singleActivityTesting.other.Constants.BASE_URL
import com.programmergabut.singleActivityTesting.other.Constants.DATABASE_NAME
import com.programmergabut.singleActivityTesting.repositories.DefaultShoppingRepository
import com.programmergabut.singleActivityTesting.repositories.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context,
    ) = Glide.with(context).setDefaultRequestOptions(
         RequestOptions()
             .placeholder(R.drawable.ic_image)
             .error(R.drawable.ic_image)
    )

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }
}
