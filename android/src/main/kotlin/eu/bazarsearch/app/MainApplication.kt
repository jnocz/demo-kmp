package eu.bazarsearch.app

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import di.initModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initFirebase()
        initKoin()
        initUncaughtException()

    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MainApplication)
            androidLogger(org.koin.core.logger.Level.ERROR)
            initModules()
        }
    }

    private fun initUncaughtException() {
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, ex ->
            Log.e("", "uncaught exception " + ex.printStackTrace() )
            defaultHandler?.uncaughtException(thread, ex)
        }
    }

    override fun onTrimMemory(level: Int) {
        Log.d ("", "trim memory: level=$level" )
        super.onTrimMemory(level)
    }
}