package com.example.tasko.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import com.example.tasko.database.ObavezaDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(coroutineScope: CoroutineScope, @ApplicationContext context: Context) =
        ObavezaDatabase.getDatabase(context, coroutineScope)

    @Provides
    @Singleton
    fun providesObavezaDao(database: ObavezaDatabase) =
        database.obavezaDao()
}