package su.wolfstudio.schedule_ice.app.dependencies

import kotlin.reflect.KClass

internal object DependenciesContainerImpl : DependenciesContainer {

    data class Dependency<T : Any>(
        val value: T
    )

    private val dependencies = hashSetOf<Dependency<*>>()

    override fun <T : Any> add(dependency: T) {
        dependencies.add(Dependency(dependency))
    }

    override fun <T : Any> set(dependency: T): T {
        dependencies.add(Dependency(dependency))
        return dependency
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getDependency(kClass: KClass<T>): T {
        return dependencies.find { kClass.isInstance(it.value) }?.value as? T
            ?: throw NoSuchDependencyException(kClass.simpleName)
    }

    override fun <T : Any> removeDependency(kClass: KClass<T>): Boolean {
        return dependencies.removeIf { kClass.isInstance(it.value) }
    }

}