package com.movies.watchme.data.realm.factory

import com.movies.watchme.BuildConfig
import com.movies.watchme.data.realm.migrations.WatchMeAppMigration
import com.movies.watchme.data.realm.module.AppRealmModule
import io.realm.RealmConfiguration
import io.realm.RealmConfiguration.Builder

object RealmConfigurationFactory {
  private const val DB_USER_MODULE = "AppRealmModule"

  fun createWatchMeAppRealmConfiguration(): RealmConfiguration {
    val appModule = Builder().modules(AppRealmModule())
      .name(DB_USER_MODULE)
      .schemaVersion(1)
      .migration(WatchMeAppMigration())
    enableDebugConfigFor(appModule)
    return appModule.build()
  }

  private fun enableDebugConfigFor(workBookModule: Builder) {
    if (BuildConfig.DEBUG) {
      workBookModule.allowQueriesOnUiThread(false)
      workBookModule.allowWritesOnUiThread(false)
    }
  }
}