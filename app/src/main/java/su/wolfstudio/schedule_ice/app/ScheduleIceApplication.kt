package su.wolfstudio.schedule_ice.app

import android.app.Application
import com.google.firebase.FirebaseApp

class ScheduleIceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        ApplicationContainer.Init(this)
    }

}