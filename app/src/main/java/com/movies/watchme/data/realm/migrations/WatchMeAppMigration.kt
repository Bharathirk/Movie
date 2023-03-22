package com.movies.watchme.data.realm.migrations

import com.movies.watchme.data.realm.models.MovieModel
import io.realm.DynamicRealm
import io.realm.FieldAttribute.PRIMARY_KEY
import io.realm.FieldAttribute.REQUIRED
import io.realm.RealmMigration

class WatchMeAppMigration : RealmMigration {
  override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
    var oldVersion = oldVersion
    val schema = realm.schema
    if (oldVersion == 0L) {
      schema.create(MovieModel::class.java.simpleName).addField("id", Int::class.java, PRIMARY_KEY)
        .addField("title", String::class.java, REQUIRED)
        .addField("posterPath", String::class.java, REQUIRED)
        .addField("backdropPath", String::class.java, REQUIRED)
        .addField("overView", String::class.java, REQUIRED)
        .addField("releaseDate", String::class.java)
        .addField("mediaType", String::class.java, REQUIRED)
        .addRealmListField("genreIds", Int::class.java)
      oldVersion++
    }
  }
}