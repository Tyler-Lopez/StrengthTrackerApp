package com.company.strengthtracker.di

import com.company.strengthtracker.data.repository.SetRepositoryImpl
import com.company.strengthtracker.domain.repository.SetRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // https://www.youtube.com/watch?v=ZE2Jkvnk2Bs 11:21 for notes on injection
    @Singleton
    @Provides
    fun provideAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideSetRepository(): SetRepository {
        return SetRepositoryImpl(db = provideDb())
    }

    @Singleton
    @Provides
    fun provideDb() = Firebase.firestore

    @Singleton
    @Provides
    fun provideUsersCollection() = FirebaseFirestore.getInstance().collection("users")
}

