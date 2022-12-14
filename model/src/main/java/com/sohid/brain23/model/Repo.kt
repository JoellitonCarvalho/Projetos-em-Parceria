package com.sohid.brain23.model

import com.sohid.brain23.model.Owner.Companion.createOwner
import org.jetbrains.annotations.TestOnly
import java.time.LocalDateTime

data class Repo(
  val id: Int,
  val owner: Owner,
  val name: String,
  val description: String,
  val createdAt: LocalDateTime,
  val updatedAt: LocalDateTime,
  val starsCount: Int,
  val watchersCount: Int,
  val forksCount: Int,
  val language: String
) {

  companion object {

    val EMPTY = Repo(
      id = -1,
      owner = Owner.EMPTY,
      name = "",
      description = "",
      createdAt = LocalDateTime.MIN,
      updatedAt = LocalDateTime.MIN,
      starsCount = 0,
      watchersCount = 0,
      forksCount = 0,
      language = ""
    )

    @TestOnly
    fun createRepo(
      id: Int = -1,
      owner: Owner = createOwner(),
      name: String = "",
      description: String = "",
      createdAt: LocalDateTime = LocalDateTime.now(),
      updatedAt: LocalDateTime = LocalDateTime.now(),
      starsCount: Int = 0,
      watchersCount: Int = 0,
      forksCount: Int = 0,
      language: String = "Android"
    ) = Repo(
      id = id,
      owner = owner,
      name = name,
      description = description,
      createdAt = createdAt,
      updatedAt = updatedAt,
      starsCount = starsCount,
      watchersCount = watchersCount,
      forksCount = forksCount,
      language = language
    )
  }
}
