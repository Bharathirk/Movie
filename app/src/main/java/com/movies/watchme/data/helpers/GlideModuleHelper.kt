package com.movies.watchme.data.helpers

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.movies.watchme.utils.NetworkUtils
import okhttp3.Call
import java.io.InputStream

@GlideModule
class GlideModuleHelper : AppGlideModule() {
  override fun applyOptions(context: Context, builder: GlideBuilder) {
    super.applyOptions(context, builder)
  }

  override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
    val okHttpClient = NetworkUtils.unsafeOkHttpClient
    val factory = OkHttpUrlLoader.Factory(okHttpClient as Call.Factory)
    registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
  }
}