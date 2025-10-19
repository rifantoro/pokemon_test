package com.example.pokemon_test.di

import android.content.Context
import androidx.room.Room
import com.example.pokemon_test.data.local.AppDatabase
import com.example.pokemon_test.data.local.SessionManager
import com.example.pokemon_test.data.local.UserDao
import com.example.pokemon_test.data.remote.PokemonApi
import com.example.pokemon_test.data.repository.PokemonRepositoryImpl
import com.example.pokemon_test.data.repository.UserRepositoryImpl
import com.example.pokemon_test.domain.repository.PokemonRepository
import com.example.pokemon_test.domain.repository.UserRepository
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

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): PokemonApi =
        retrofit.create(PokemonApi::class.java)


    @Provides
    @Singleton
    fun providePokemonRepository(
        api: PokemonApi
    ): PokemonRepository {
        return PokemonRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }
}