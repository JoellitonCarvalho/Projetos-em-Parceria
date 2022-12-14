package com.sohid.brain23.base.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.sohid.brain23.base.R
import com.sohid.brain23.model.Owner
import com.sohid.brain23.model.Repo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RepoCell(
  modifier: Modifier,
  repo: Repo,
  onClicked: (repo: Repo) -> Unit,
  onLongClicked: (repo: Repo) -> Unit,
) {
  Column(
    modifier = modifier
      .combinedClickable(
        onClick = { onClicked(repo) },
        onLongClick = { onLongClicked(repo) },
      )
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Image(
        modifier = Modifier
          .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
          .size(24.dp),
        painter = rememberImagePainter(
          data = repo.owner.avatarUrl,
          builder = {
            transformations(RoundedCornersTransformation(16f))
          }
        ),
        contentDescription = null,
      )
      Text(
        modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
        text = repo.owner.login,
        style = MaterialTheme.typography.subtitle2,
      )
    }
    Text(
      modifier = Modifier.padding(horizontal = 16.dp),
      text = repo.name,
      style = MaterialTheme.typography.h6,
    )
    if (repo.description.isNotBlank()) {
      Text(
        modifier = Modifier
          .padding(top = 4.dp)
          .padding(horizontal = 16.dp),
        text = repo.description,
        style = MaterialTheme.typography.body2,
      )
    }
    Row(
      modifier = Modifier
        .padding(bottom = 8.dp)
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
    ) {

      Text(
        modifier = Modifier
          .padding(start = 4.dp)
          .padding(vertical = 8.dp),
        text = "Android",
        style = MaterialTheme.typography.body2,
      )
      Image(
        modifier = Modifier
          .padding(start = 8.dp)
          .padding(vertical = 8.dp)
          .size(12.dp),
        painter = painterResource(R.drawable.star),
        contentDescription = null,
      )
      Text(
        modifier = Modifier
          .padding(start = 4.dp)
          .padding(vertical = 8.dp),
        text = repo.starsCount.toString(),
        style = MaterialTheme.typography.body2,
      )
    }
  }
}

@Preview
@Composable
fun RepoCellPreview() {
  RepoCell(
    modifier = Modifier.fillMaxWidth(),
    repo = Repo.createRepo(
      id = 1,
      owner = Owner.createOwner(
        id = 1,
        login = "Owner Name",
        avatarUrl = "https://avatars3.githubusercontent.com/u/13956869",
      ),
      name = "Repo Name",
      description = "Repo Description",
      language = "Android"
    ),
    onClicked = {},
    onLongClicked = {},
  )
}
