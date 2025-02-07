import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jlleitschuh.gradle:ktlint-gradle:12.1.0")
    }
}

plugins {
    application
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.jetbrains.kotlinx.kover") version "0.7.1"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

group = "com.donateraja"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

application {
    mainClass.set("com.donateraja.ApplicationKt")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("org.springdoc:springdoc-openapi-ui:2.0.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
    implementation("org.postgresql:postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")

    implementation("org.flywaydb:flyway-core")
    implementation("org.projectlombok:lombok")

    implementation("com.backblaze.b2:b2-sdk-core:6.3.0")
    implementation("com.backblaze.b2:b2-sdk-httpclient:6.3.0")
    // JWT dependencies (JJWT API)
//    implementation("org.springframework.security:spring-security-oauth2-jose")
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
//    implementation("com.nimbusds:nimbus-jose-jwt:9.1.2")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.mockito", module = "mockito-core")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-Xjsr305=strict", "-opt-in=kotlin.time.ExperimentalTime", "-Xjvm-default=all")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kover {
    useJacoco("0.8.10")
}

tasks.check {
    dependsOn(tasks.ktlintCheck)
}

koverReport {
    filters {
        excludes {
            packages("com.donateraja.domain")
            packages("com.donateraja.configurations")
            classes("com.donateraja.repository.*")
            classes("com.donateraja.*.*\$logger\$1*")
            classes("com.donateraja.ApplicationKt")
            classes("com.donateraja.Application")
        }
    }
    defaults {
        verify {
            onCheck = true
            rule {
                isEnabled = true
                entity = kotlinx.kover.gradle.plugin.dsl.GroupingEntityType.APPLICATION
                bound {
                    minValue = 90
                    metric = kotlinx.kover.gradle.plugin.dsl.MetricType.LINE
                    aggregation = kotlinx.kover.gradle.plugin.dsl.AggregationType.COVERED_PERCENTAGE
                }
            }
        }
    }
}
