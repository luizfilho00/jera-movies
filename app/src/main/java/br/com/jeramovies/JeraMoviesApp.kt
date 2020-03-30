package br.com.jeramovies

import android.app.Application
import br.com.jeramovies.presentation.di.apiModule
import br.com.jeramovies.presentation.di.appModule
import br.com.jeramovies.presentation.di.repositoryModule
import br.com.jeramovies.presentation.di.viewModelModule
import com.facebook.stetho.Stetho
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class JeraMoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidLogger()
            androidContext(this@JeraMoviesApp)
            modules(
                listOf(
                    apiModule,
                    repositoryModule,
                    viewModelModule,
                    appModule
                )
            )
        }
    }
}