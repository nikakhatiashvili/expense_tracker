package com.example.finaleproject.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AuthModulea {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth



}