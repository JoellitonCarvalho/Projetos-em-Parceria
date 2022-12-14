package com.sohid.brain23.history

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sohid.brain23.R
import com.sohid.brain23.base.ui.LocalActivity
import com.sohid.brain23.base.ui.component.RepoCell
import com.sohid.brain23.base.ui.component.TopBar
import com.sohid.brain23.base.util.collectAsLifecycleAwareState
import com.sohid.brain23.base.viewmodel.NavigationViewModel
import com.sohid.brain23.base.viewmodel.SystemViewModel
import com.sohid.brain23.base.viewmodel.UserUiState
import com.sohid.brain23.base.viewmodel.UserViewModel
import com.sohid.brain23.model.Repo

@Composable
fun HistoryScreen(
  userViewModel: UserViewModel = viewModel(LocalActivity.current),
  systemViewModel: SystemViewModel = viewModel(LocalActivity.current),
  navigationViewModel: NavigationViewModel = viewModel(LocalActivity.current),
) {
  val uiState by userViewModel.uiState.collectAsLifecycleAwareState()

  val context = LocalContext.current
  val listState = rememberLazyListState()

  Scaffold(

    content = { paddingValues ->
      HistoryBody(
        modifier = Modifier.padding(paddingValues),
        uiState = uiState,
        listState = listState,
        onRepoClicked = { repo -> navigationViewModel.openRepo(repo.owner.login, repo.name) },
        onRepoLongClicked = { repo ->
          userViewModel.removeHistory(repo)
          Toast.makeText(context, R.string.delete_repository, Toast.LENGTH_SHORT).show()
        }
      )
    }
  )
}

@Composable
fun PinTopBar(
  onNightModePressed: () -> Unit,
  listState: LazyListState,
) {
  TopBar(
    title = { Text(text = stringResource(id = R.string.history)) },
    elevation = if (
      listState.firstVisibleItemIndex == 0 &&
      listState.firstVisibleItemScrollOffset < 25
    ) {
      0.dp
    } else {
      AppBarDefaults.TopAppBarElevation
    },
  )
}

@Composable
fun HistoryBody(
  modifier: Modifier,
  uiState: UserUiState,
  listState: LazyListState,
  onRepoClicked: (repo: Repo) -> Unit,
  onRepoLongClicked: (repo: Repo) -> Unit,
) {
  Box(modifier = modifier) {
    if (uiState.pinnedRepos.isEmpty()) {
      PinEmptyLayout(modifier = Modifier.fillMaxSize())
    } else {
      PinRepoItems(
        modifier = Modifier.fillMaxWidth(),
        uiState = uiState,
        listState = listState,
        onRepoClicked = onRepoClicked,
        onRepoLongClicked = onRepoLongClicked
      )
    }
  }
}

@Composable
fun PinRepoItems(
  modifier: Modifier,
  uiState: UserUiState,
  listState: LazyListState,
  onRepoClicked: (repo: Repo) -> Unit,
  onRepoLongClicked: (repo: Repo) -> Unit,
) {
  LazyColumn(
    modifier = modifier,
    state = listState,
  ) {
    for (pinnedRepo in uiState.pinnedRepos) {
      item {
        RepoCell(
          modifier = Modifier.fillMaxWidth(),
          repo = pinnedRepo,
          onClicked = onRepoClicked,
          onLongClicked = onRepoLongClicked,
        )
        Divider()
      }
    }
  }
}

@Composable
fun PinEmptyLayout(
  modifier: Modifier,
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {

    Text(
      modifier = Modifier
        .padding(horizontal = 32.dp)
        .padding(top = 16.dp),
      text = stringResource(id = R.string.empty_repository_text),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.h6,
      fontWeight = FontWeight.Bold,
    )
    Text(
      modifier = Modifier
        .padding(horizontal = 32.dp)
        .padding(top = 16.dp),
      text = stringResource(id = R.string.empty_repository_details),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.body2,
    )
  }
}
