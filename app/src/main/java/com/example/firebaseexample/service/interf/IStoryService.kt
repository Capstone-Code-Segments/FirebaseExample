package com.example.firebaseexample.service.interf

import com.example.firebaseexample.data.StoryData
import kotlinx.coroutines.flow.Flow

interface IStoryService {
    val stories: Flow<List<StoryData>>

    suspend fun getStory(storyId: String): StoryData?
    suspend fun upload(storyData: StoryData): String
    suspend fun update(storyData: StoryData)
    suspend fun delete(storyId: String)
}