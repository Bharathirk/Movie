package com.movies.watchme.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.movies.watchme.presentation.customviews.CustomProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

  private var progressDialog: CustomProgressDialog? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  fun showLoading() {
    try {
      if (progressDialog == null) {
        progressDialog = CustomProgressDialog.create(context = this, cancelable = false)
        progressDialog?.show()
      } else {
        progressDialog?.show()
      }
    } catch (exception: Exception) {
      exception.printStackTrace()
    }
  }

  fun hideLoading() {
    progressDialog?.hide()
  }

  fun setHeaderTitle(title: String?) {
    if (supportActionBar != null) supportActionBar!!.title = title
  }

  fun showBackButton(status: Boolean) {
    if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(status)
  }

  fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
  }

  fun hideSoftInput() {
    try {
      val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      manager.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)
    } catch (exception: NullPointerException) {
      exception.printStackTrace()
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      finish()
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onDestroy() {
    super.onDestroy()
    hideLoading()
    progressDialog = null
  }
}