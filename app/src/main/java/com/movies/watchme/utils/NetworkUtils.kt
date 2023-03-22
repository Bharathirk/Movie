package com.movies.watchme.utils

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit.MINUTES
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object NetworkUtils {
  private const val CONNECTION_TIMEOUT = 1

  /**
   * Check the Internet connection available status
   *
   * @param context - Context environment passed by this parameter
   * @return boolean true if the Internet Connection is Available and false otherwise
   */
  fun isConnected(context: Context): Boolean {
    //Connectivity manager instance
    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    // Fetch Active internet connection from network info
    val netInfo = manager.activeNetworkInfo
    // return the network connection is active or not.
    return netInfo != null && netInfo.isConnectedOrConnecting
  }

  // Create a trust manager that does not validate certificate chains
  val unsafeOkHttpClient: OkHttpClient
    // Install the all-trusting trust manager
    // Create an ssl socket factory with our all-trusting manager
    get() = try {
      // Create a trust manager that does not validate certificate chains
      val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(
          chain: Array<X509Certificate>, authType: String
        ) {
        }

        override fun checkServerTrusted(
          chain: Array<X509Certificate>, authType: String
        ) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
          return arrayOf()
        }
      })
      val logging = HttpLoggingInterceptor()
      logging.setLevel(BODY)

      // Install the all-trusting trust manager
      val sslContext = SSLContext.getInstance("SSL")
      sslContext.init(null, trustAllCerts, SecureRandom())

      // Create an ssl socket factory with our all-trusting manager
      val sslSocketFactory = sslContext.socketFactory
      val builder = Builder()
      builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
      builder.hostnameVerifier { _: String?, _: SSLSession? -> true }
      builder.connectTimeout(CONNECTION_TIMEOUT.toLong(), MINUTES)
        .readTimeout(CONNECTION_TIMEOUT.toLong(), MINUTES)
        .writeTimeout(CONNECTION_TIMEOUT.toLong(), MINUTES).addInterceptor(logging)
      builder.build()
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
}