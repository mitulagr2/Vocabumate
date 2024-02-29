package com.example.vocabumate

import android.app.Application
import com.example.vocabumate.data.AppContainer
import com.example.vocabumate.data.DefaultAppContainer

class VocabumateApplication : Application() {
  /** AppContainer instance used by the rest of classes to obtain dependencies */
  lateinit var container: AppContainer
  override fun onCreate() {
    super.onCreate()
    container = DefaultAppContainer(this)
  }
}
