plugins {
    java
    application
}

group = "me.duncte123"
version = "1.0-SNAPSHOT"

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