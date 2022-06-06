package com.ubi.bgg.database.adapters

import com.ubi.bgg.Common
import com.ubi.bgg.database.entities.Game
import com.ubi.bgg.database.entities.Rank
import com.ubi.bgg.services.bgg.user.Thing
import com.ubi.bgg.utils.Date

fun migrate(things: List<Thing>) {
  val games = Common.Database.games().readAll()
  val ranks = Common.Database.ranks().readAll()

  games.forEach { game ->
    if (!things.any { game.BGGId == it.id }) {
      Common.Database.ranks().remove(ranks.filter { it.gameId == game.id })
      Common.Database.games().remove(game)
    }
  }

  things.forEach(::migrate)
}

private fun migrate(thing: Thing) {
  val gameDAO = Common.Database.games()

  if (!gameDAO.contains(thing.id!!)) gameDAO.create(game(thing))

  val game = gameDAO.read(thing.id!!)
  if (thing.rank != null) Common.Database.ranks().create(rank(thing, game))
}

private fun rank(thing: Thing, game: Game): Rank =
  Rank(thing.rank!!, thing.bayesaverage!!, Date.format(Date.local()), game.id)

private fun game(thing: Thing): Game {
  println("${thing.name} (${thing.isExpansion})")
  return Game(thing.name!!, thing.thumbnail, thing.yearpublished, thing.id!!, thing.isExpansion!!)
}
