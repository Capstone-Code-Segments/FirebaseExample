package com.example.firebaseexample.service.interf

import com.example.firebaseexample.data.UserData
import kotlinx.coroutines.flow.Flow

interface IAccountService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<UserData>

    suspend fun register(email: String, password: String)
    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}