plugins {
    application
}

dependencies {
    implementation(project(":java-base"))
    implementation(project(":java-data"))
}

application {
    applicationName = "server"
    mainClassName = "net.cryptic_game.backend.server.App"
}

tasks {
    jar {
        manifest {
            attributes("Implementation-Version" to project.version.toString())
        }
    }
}
