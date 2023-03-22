package com.movies.watchme.data.realm.mapper

import com.movies.watchme.utils.toRealmList
import io.realm.RealmList

fun Iterable<Int>.toPojos(): List<Int> =
  this.map { it }

fun Iterable<Int>.toRealmList(): RealmList<Int> =
  this.toRealmList()