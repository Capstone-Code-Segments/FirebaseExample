package com.example.firebaseexample.data

import com.google.firebase.firestore.DocumentId

data class StoryData (
    @DocumentId val id: String = "",
    val description: String = "",
    val date: String = "",
    val userId: String = "",
)