plugins {
    kotlin("jvm") version "1.7.10"
    application
}

repositories{
    mavenCentral()
}

application{
    mainClass.set("tim.HappyKt")

}

tasks{
    jar{
        manifest.attributes["Main-Class"] = "tim.HappyKt"
        val dependencies = configurations
            .runtimeClasspath
            .get()
            .map(::zipTree) // OR .map { zipTree(it) }
        from(dependencies)
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}
