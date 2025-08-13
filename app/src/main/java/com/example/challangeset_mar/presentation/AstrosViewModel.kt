package com.example.challangeset_mar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challangeset_mar.data.models.AstroListResult
import com.example.challangeset_mar.domain.usecase.GetAstrosUseCase
import com.example.challangeset_mar.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AstrosUiState(
    val isLoading: Boolean = false,
    val astrosData: AstroListResult? = null,
    val error: String? = null
)

@HiltViewModel
class AstrosViewModel @Inject constructor(
    private val getAstrosUseCase: GetAstrosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AstrosUiState())
    val uiState: StateFlow<AstrosUiState> = _uiState.asStateFlow()

    init {
        getAstros()
    }

    private fun getAstros() {
        viewModelScope.launch {
            getAstrosUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                    }

                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            astrosData = resource.data,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                }
            }
        }
    }
}