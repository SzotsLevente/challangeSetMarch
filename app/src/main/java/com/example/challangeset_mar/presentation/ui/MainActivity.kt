package com.example.challangeset_mar.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.challangeset_mar.presentation.AstrosViewModel
import com.example.challangeset_mar.ui.theme.ChallangeSet_marTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.graphics.Color

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: AstrosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChallangeSet_marTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Transparent
                ) { innerPadding ->
                    val uiState by viewModel.uiState.collectAsState()
                    
                   AstrosScreen(modifier = Modifier.padding(innerPadding),
                       uiState)
                }
            }
        }
    }
}
