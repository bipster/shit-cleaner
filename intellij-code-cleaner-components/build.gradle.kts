import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val artifactGroup: String by project
val artifactVersion: String by project
val intellijVersion: String by project
val jvmTarget: String by project
val foundationVersion: String by project
val foundationProcessorVersion: String by project
val jodaTimeVersion: String by project

group = artifactGroup
version = artifactVersion

repositories {
    jcenter()
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.github.nhat-phan.foundation:foundation-jvm:$foundationVersion")
    implementation(project(":contracts"))
    implementation(project(":core"))
    implementation("joda-time:joda-time:$jodaTimeVersion")

    kapt("com.github.nhat-phan.foundation:foundation-processor:$foundationProcessorVersion")
    kaptTest("com.github.nhat-phan.foundation:foundation-processor:$foundationProcessorVersion")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

kapt {
    arguments {
        arg("foundation.processor.globalNamespace", "net.ntworld.codeCleaner.components")
    }
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = intellijVersion
}

val compileKotlin: KotlinCompile by tasks
val compileTestKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = jvmTarget
}

compileTestKotlin.kotlinOptions {
    jvmTarget = jvmTarget
}
