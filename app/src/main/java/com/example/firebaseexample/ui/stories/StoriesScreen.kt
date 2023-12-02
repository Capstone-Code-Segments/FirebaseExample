package com.example.firebaseexample.ui.stories

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.firebaseexample.ViewModelFactory
import com.example.firebaseexample.data.StoryData

@Composable
fun StoriesScreen(
    openScreen: (String) -> Unit,
    storiesViewModel: StoriesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance()
    )
) {
    val stories = storiesViewModel.stories.collectAsStateWithLifecycle(emptyList())

    StoriesContent(stories = stories.value, openScreen)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StoriesContent(stories: List<StoryData>, openScreen: (String) -> Unit) {
//    val bottomSheetScaffoldStateMetode: ModalBottomSheetState = rememberModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden
//    )

    Scaffold { _ ->
        Surface(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column() {
                        //Image()

                        Text("m", modifier = Modifier)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {

    }
}
