plugins {
    id("java")
    id("dev.architectury.loom") version("1.2-SNAPSHOT")
    id("architectury-plugin") version("3.4-SNAPSHOT")
    kotlin("jvm") version ("1.8.10")
}

group = "necro.livelier.pokemon"
version = "1.0.0"

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    silentMojangMappingsLicense()
    mixin {
        defaultRefmapName.set("mixins.${project.name}.refmap.json")
    }
    accessWidenerPath.set(file("src/main/resources/livelier-pokemon.accesswidener"))
}

repositories {
    mavenCentral()
    maven(url = "https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
    maven("https://maven.impactdev.net/repository/development/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    minecraft("net.minecraft:minecraft:1.20.1")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:0.14.21")
    implementation("com.google.code.gson:gson:2.8.5")

    modImplementation("net.fabricmc.fabric-api:fabric-api:0.89.3+1.20.1")
    modImplementation(fabricApi.module("fabric-command-api-v2", "0.89.3+1.20.1"))
    modImplementation("com.cobblemon:fabric:1.4.0+1.20.1-SNAPSHOT")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

tasks {
    // The AW file is needed in :fabric project resources when the game is run.
    // val copyAccessWidener by registering(Copy::class) {
    //     destinationDir = file("build/libs")
    //     from(loom.accessWidenerPath)
    // }

    // processResources {
    //     dependsOn(copyAccessWidener)
    // }
}