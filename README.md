<h1 align="center">
    Cryptic Java Backend
</h1>

<p align="center">
    <a style="text-decoration:none" href="https://github.com/cryptic-game/java-backend-dev/releases">
        <img alt="Releases" src="https://img.shields.io/github/v/tag/cryptic-game/java-backend-dev?label=latest%20version&style=flat-square">
    </a>
    <a style="text-decoration:none" href="https://github.com/cryptic-game/java-backend-dev/actions">
        <img alt="Build" src="https://img.shields.io/github/workflow/status/cryptic-game/java-backend-dev/Build/master?style=flat-square">
    </a>
    <a style="text-decoration:none" href="https://hub.docker.com/r/crypticcp/server">
        <img alt="DockerHub - Server" src="https://img.shields.io/docker/pulls/crypticcp/server?style=flat-square&label=DockerHub%20-%20Server">
    </a>
    <a style="text-decoration:none" href="https://hub.docker.com/r/crypticcp/java-daemon">
        <img alt="DockerHub - Java Daemon" src="https://img.shields.io/docker/pulls/crypticcp/java-daemon?style=flat-square&label=DockerHub%20-%20Java%20Daemon">
    </a>
</p>
<p align="center">
    This is the <a href="https://cryptic-game.github.io/java-backend-dev/">Java Backend</a> of the Cryptic project.
</p>

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

* A Java IDE ([IntelliJ IDEA](https://www.jetbrains.com/idea/))
* [JDK 11](https://adoptopenjdk.net/index.html)
* [Git](https://git-scm.com/)

### Installing

A step by step series of examples that tell you how to get a development env running

```sh
git clone https://github.com/cryptic-game/java-backend-dev.git
```

Then you can open it with you IDE.

### Running

To test the things you have changed you have to execute the Gradle Task 'run'.

Server:
````sh
./gradlew :server:start
````

Java-Daemon:
````sh
./gradlew :java-daemon:start
````

After the first run you will find a `config.yaml` in the directories `java-server` and `java-daemon`, where you can customize your development environment. 

### Documentation

The Javadocs can be found in [GitHub Pages](https://cryptic-game.github.io/java-backend-dev/) and the protocol documentation in our [wiki](https://wiki.cryptic-game.net/books/einf%C3%BChrungen/chapter/java).

### Deployment

See Cryptic backend deployment. (TODO Link)

## Used Tools

* [Gradle](https://gradle.org/) - The build tool
* [Github Actions](https://github.com/features/actions/) - The CI tool
* [Docker](https://docker.com/) - The deployment tool

## Authors

_**The Cryptic Java Team**_
