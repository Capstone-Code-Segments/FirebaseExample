package com.example.firebaseexample.service.implementation

import androidx.core.os.trace
import com.example.firebaseexample.data.StoryData
import com.example.firebaseexample.service.interf.IAccountService
import com.example.firebaseexample.service.interf.IStoryService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await

class StoryService(
    private val firebaseFirestore: FirebaseFirestore,
    private val iAccountService: IAccountService
) :
    IStoryService {
    override val stories: Flow<List<StoryData>>
        get() =
            iAccountService.currentUser.flatMapLatest { user ->
                firebaseFirestore.collection(STORY_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id)
                    .dataObjects()
            }

    override suspend fun getStory(id: String): StoryData? =
        firebaseFirestore.collection(STORY_COLLECTION).document(id).get().await().toObject()

    override suspend fun upload(storyData: StoryData): String = trace(UPLOAD_STORY_TRACE) {
        val storyWithUserId = storyData.copy(userId = iAccountService.currentUserId)
        firebaseFirestore.collection(STORY_COLLECTION).add(storyWithUserId).await().id
    }

    override suspend fun update(storyData: StoryData): Unit =
        trace(EDIT_STORY_TRACE) {
            firebaseFirestore.collection(STORY_COLLECTION).document(storyData.id).set(storyData).await()
        }

    override suspend fun delete(id: String) {
        firebaseFirestore.collection(STORY_COLLECTION).document(id).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val STORY_COLLECTION = "stories"
        private const val UPLOAD_STORY_TRACE = "uploadStory"
        private const val EDIT_STORY_TRACE = "updateStory"

        @Volatile
        private var instance: StoryService? = null

        fun getInstance(
            firebaseFirestore: FirebaseFirestore,
            iAccountService: IAccountService
        ): StoryService =
            instance ?: synchronized(this) {
                instance ?: StoryService(firebaseFirestore, iAccountService)
            }
    }
}