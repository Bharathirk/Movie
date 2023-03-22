package com.movies.watchme.data.di.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.movies.watchme.BuildConfig
import com.movies.watchme.app.AppController
import com.movies.watchme.data.api.MoviesApi
import com.movies.watchme.data.eventbus.MainThreadBus
import com.movies.watchme.data.realm.factory.RealmConfigurationFactory
import com.movies.watchme.data.repo.MoviesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val CONNECT_TIMEOUT = 2
private const val READ_TIMEOUT = 2
private const val WRITE_TIMEOUT = 2

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

  private val bus: MainThreadBus = MainThreadBus()

  @Provides @Singleton fun providesContext(): Context {
    return AppController.getInstance()!!
  }

  @Provides @Singleton fun providesBus() = bus

  @Provides @Singleton fun providesGson(): Gson {
    return GsonBuilder().serializeNulls().setLenient().create()
  }

  @Provides @Singleton fun providesLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(
      if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    )
    return logging
  }

  @Provides @Singleton fun provideOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor(logger)
      .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.MINUTES)
      .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.MINUTES)
      .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.MINUTES).build()
  }

  @Provides @Singleton fun provideRetrofit(
    okHttpClient: OkHttpClient, gson: Gson
  ): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
      .addCallAdapterFactory(RxJavaCallAdapterFactory.createAsync())
      .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()
  }

  @Provides @Singleton fun provideMoviesApi(retrofit: Retrofit): MoviesApi =
    retrofit.create(MoviesApi::class.java)

  @Provides @Singleton fun providesAppRealmConfig(): RealmConfiguration {
    return RealmConfigurationFactory.createWatchMeAppRealmConfiguration()
  }

  @Provides @Singleton fun providesMoviesRepo(
    moviesApi: MoviesApi,
    appRealmConfig: RealmConfiguration,
  ): MoviesRepo {
    return MoviesRepo(api = moviesApi, realmConfiguration = appRealmConfig)
  }
}