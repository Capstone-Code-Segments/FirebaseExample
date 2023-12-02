package com.example.firebaseexample

import com.example.firebaseexample.service.implementation.AccountService
import com.example.firebaseexample.service.implementation.StoryService
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

object Injection {
    fun provideAccountService(): AccountService {
        return AccountService.getInstance(Firebase.auth)
    }

    fun provideStoryService(): StoryService {
        return StoryService.getInstance(Firebase.firestore, provideAccountService())
    }
}