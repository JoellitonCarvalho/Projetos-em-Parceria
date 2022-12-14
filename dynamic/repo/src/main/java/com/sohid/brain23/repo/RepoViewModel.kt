package com.sohid.brain23.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sohid.brain23.base.ErrorHandler
import com.sohid.brain23.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
  private val owner: String,
  private val repo: String,
  private val repoRepository: RepoRepository,
  private val errorHandler: ErrorHandler
) : ViewModel() {

  private val _uiState: MutableStateFlow<RepoUiState> = MutableStateFlow(RepoUiState.EMPTY)
  val uiState: StateFlow<RepoUiState> get() = _uiState

  init {
    viewModelScope.launch {
      runCatching {
        _uiState.value = _uiState.value.copy(isLoading = true)
        Pair(
          repoRepository.getRepo(owner, repo),
          ""
        )
      }.onSuccess { (repo, it) ->
        _uiState.value = _uiState.value.copy(
          repo = repo,
          isLoading = false
        )
      }.onFailure { error ->
        _uiState.value = _uiState.value.copy(isLoading = false)
        errorHandler.handleError(error)
      }
    }
  }
}
