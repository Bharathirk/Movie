package com.movies.watchme.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.movies.watchme.databinding.ActivitySplashBinding
import com.movies.watchme.presentation.base.BaseActivity
import com.movies.watchme.presentation.home.HomeActivity

class SplashActivity : BaseActivity() {

  private lateinit var binding: ActivitySplashBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySplashBinding.inflate(layoutInflater)
    setContentView(binding.root)

    Handler(Looper.getMainLooper()).postDelayed({
      startActivity(HomeActivity.getCallingIntent(this))
      finish()
    }, 1000)
  }
}