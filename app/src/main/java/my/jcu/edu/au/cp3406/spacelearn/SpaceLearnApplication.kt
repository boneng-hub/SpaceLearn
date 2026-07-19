package my.jcu.edu.au.cp3406.spacelearn

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SpaceLearnApplication : Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()

        appContainer = AppContainer(
            context = this
        )
    }
}