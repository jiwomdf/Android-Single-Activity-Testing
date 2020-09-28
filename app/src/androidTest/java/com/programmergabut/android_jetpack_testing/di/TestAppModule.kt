package com.programmergabut.android_jetpack_testing.di

import android.content.Context
import androidx.room.Room
import com.programmergabut.android_jetpack_testing.data.local.ShoppingItemDatabases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDB(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, ShoppingItemDatabases::class.java)
            .allowMainThreadQueries()
            .build()


}