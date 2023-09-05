package su.wolfstudio.schedule_ice.ui.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel

abstract class ViewModelBase: ViewModel(), LifecycleObserver {
    protected open val tag = javaClass.simpleName
}