package su.wolfstudio.schedule_ice.app

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import su.wolfstudio.schedule_ice.cashe.ApplicationCacheImpl
import su.wolfstudio.schedule_ice.app.dependencies.DependenciesContainer
import su.wolfstudio.schedule_ice.bd.DataBaseFirebaseImpl

interface ApplicationContainer {

    class Init(context: Context) {
        private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

        private val dependenciesContainer = DependenciesContainer()

        private val applicationCache = ApplicationCacheImpl()
        private val dataBaseFirebase = DataBaseFirebaseImpl(applicationCache)

        init {
            dependenciesContainer.add(dataBaseFirebase)
            dependenciesContainer.add(applicationCache)
        }

    }

}