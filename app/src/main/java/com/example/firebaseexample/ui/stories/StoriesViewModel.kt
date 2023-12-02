package com.example.firebaseexample.ui.stories

import androidx.lifecycle.ViewModel
import com.example.firebaseexample.service.interf.IStoryService

class StoriesViewModel(private val iStoryService: IStoryService) : ViewModel() {
    val stories = iStoryService.stories

    fun loadStoryOptions() {

    }
}