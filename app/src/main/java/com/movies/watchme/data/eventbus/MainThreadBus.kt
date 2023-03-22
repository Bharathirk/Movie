package com.movies.watchme.data.eventbus

import android.os.Handler
import android.os.Looper
import com.squareup.otto.Bus

class MainThreadBus : Bus() {

  private val mainThreadHandler = Handler(Looper.getMainLooper())

  override fun post(event: Any) {
    if (Looper.myLooper() != Looper.getMainLooper()) {
      mainThreadHandler.post { post(event) }
    } else {
      super.post(event)
    }
  }
}