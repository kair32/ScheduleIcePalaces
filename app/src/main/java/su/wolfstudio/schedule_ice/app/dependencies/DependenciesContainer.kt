package su.wolfstudio.schedule_ice.app.dependencies

import kotlin.reflect.KClass

fun DependenciesContainer(): DependenciesContainer = DependenciesContainerImpl

interface DependenciesContainer {

    fun <T : Any> add(dependency: T)
    fun <T : Any> set(dependency: T): T
    fun <T : Any> getDependency(kClass: KClass<T>): T
    fun <T : Any> removeDependency(kClass: KClass<T>): Boolean

}

inline fun <reified T : Any> getDependency(): T = DependenciesContainer().getDependency(T::class)
inline fun <reified T : Any> injectDependency(): Lazy<T> = lazy { DependenciesContainer().getDependency(T::class) }
fun <T : Any> setDependency(dependency: T): T = DependenciesContainer().set(dependency)
inline fun <reified T : Any> removeDependency(): Boolean = DependenciesContainer().removeDependency(T::class)
/*fun <T : Any> addDependency(dependency: T) = DependenciesContainer().add(dependency)*/
