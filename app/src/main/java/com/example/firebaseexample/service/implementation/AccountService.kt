package com.example.firebaseexample.service.implementation

import com.example.firebaseexample.data.UserData
import com.example.firebaseexample.service.interf.IAccountService
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AccountService(private val firebaseAuth: FirebaseAuth) : IAccountService {

    override val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = firebaseAuth.currentUser != null

    override val currentUser: Flow<UserData>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { firebaseAuth ->
                    this.trySend(firebaseAuth.currentUser?.let { UserData(it.uid, it.isAnonymous) }
                        ?: UserData())
                }
            firebaseAuth.addAuthStateListener(listener)
            awaitClose { firebaseAuth.removeAuthStateListener(listener) }
        }

    override suspend fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun authenticate(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    override suspend fun createAnonymousAccount() {
        firebaseAuth.signInAnonymously().await()
    }

    override suspend fun linkAccount(email: String, password: String): Unit =
        com.example.firebaseexample.util.trace(LINK_ACCOUNT_TRACE) {
            val credential = EmailAuthProvider.getCredential(email, password)
            firebaseAuth.currentUser?.linkWithCredential(credential)?.await()
        }

    override suspend fun deleteAccount() {
        firebaseAuth.currentUser!!.delete().await()
    }

    override suspend fun signOut() {
        if (firebaseAuth.currentUser!!.isAnonymous) {
            firebaseAuth.currentUser!!.delete()
        }
        firebaseAuth.signOut()

        // Sign the user back in anonymously.
        // createAnonymousAccount()
    }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"

        @Volatile
        private var instance: AccountService? = null

        fun getInstance(
            firebaseAuth: FirebaseAuth,
        ): AccountService =
            instance ?: synchronized(this) {
                instance ?: AccountService(firebaseAuth)
            }
    }
}