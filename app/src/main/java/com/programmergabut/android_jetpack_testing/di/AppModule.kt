package com.programmergabut.android_jetpack_testing.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.programmergabut.android_jetpack_testing.R
import com.programmergabut.android_jetpack_testing.data.local.ShoppingDao
import com.programmergabut.android_jetpack_testing.data.local.ShoppingItemDatabase
import com.programmergabut.android_jetpack_testing.data.remote.PixabayAPI
import com.programmergabut.android_jetpack_testing.other.Constants.BASE_URL
import com.programmergabut.android_jetpack_testing.other.Constants.DATABASE_NAME
import com.programmergabut.android_jetpack_testing.repositories.DefaultShoppingRepository
import com.programmergabut.android_jetpack_testing.repositories.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
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
