plugins {
    java
    application

    id("com.github.johnrengelman.shadow") version "5.0.0"
}

group = "me.duncte123"
version = "1.0-SNAPSHOT"

val shadowJar: com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar by tasks

application {
    mainClassName = "me.duncte123.linecounter.Main"
}

repositories {
    jcenter()
}

dependencies {
    //
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

shadowJar.apply {
    archiveClassifier.set("")
    archiveVersion.set("")
}