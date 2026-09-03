pluginManagement {
	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.fabricmc.net/") { name = "Fabric" }
		maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
		maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
		maven("https://maven.parchmentmc.org") { name = "ParchmentMC" }
	}
	includeBuild("build-logic")
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
	id("dev.kikugie.stonecutter") version "0.9.2"
	id("dev.kikugie.loom-back-compat") version "0.4.1"
}

stonecutter {
	create(rootProject) {
		fun fabric(version: String) {
			version("$version-fabric", version).buildscript = "build.fabric.gradle.kts"
		}

		fabric("26.2")
		fabric("26.1.2")
		fabric("1.21.11")
		fabric("1.21.10")
		fabric("1.21.7")
		fabric("1.21.4")
		fabric("1.21.1")

		vcsVersion = "1.21.7-fabric"
	}
}
