package su.wolfstudio.schedule_ice.app.dependencies

class NoSuchDependencyException(className: String?) : RuntimeException(
    "$className not found in dependency container"
)