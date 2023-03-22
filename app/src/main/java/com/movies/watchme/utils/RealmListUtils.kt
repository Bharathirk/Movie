package com.movies.watchme.utils

import io.realm.RealmList
import io.realm.RealmObject

inline fun <T, R : RealmObject> Iterable<T>.toRealmList(mapper: (T) -> R): RealmList<R> {
  val returnList = RealmList<R>()
  forEach {
    returnList.add(mapper(it))
  }
  return returnList
}

fun <T> Iterable<T>.toRealmList(): RealmList<T> {
  val returnList = RealmList<T>()
  forEach {
    returnList.add(it)
  }
  return returnList
}