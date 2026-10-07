plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.dcelysia.csust_spider"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
}

dependencies {
    testImplementation(libs.junit)
    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.converter.scalars)
    // jsoup
    implementation(libs.jsoup)
    //MMKV
    implementation(libs.mmkv)
    //FastKv
    implementation(libs.fastkv)
    // Gson
    implementation(libs.gson)
    implementation(libs.mmkv)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}
publishing{
    publications{
        register<MavenPublication>("release"){
            groupId = "com.declysia.csust"
            artifactId = "spider"
            version = "1.0.0"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

// Run the pure configuration tests with a short JVM classpath. Gradle 8.13's Test worker
// cannot load these classes through its argument file in this existing Windows Unicode path.
tasks.register<JavaExec>("verifyCampusCardAuth") {
    group = "verification"
    dependsOn("compileDebugUnitTestKotlin")
    mainClass.set("org.junit.runner.JUnitCore")
    args("com.dcelysia.csust_spider.core.CampusCardAuthConfigTest")
    classpath = files(
        layout.buildDirectory.dir("tmp/kotlin-classes/debugUnitTest"),
        layout.buildDirectory.dir("tmp/kotlin-classes/debug"),
        providers.provider {
            tasks.named<Test>("testDebugUnitTest").get().classpath.filter { dependency ->
                dependency.name.startsWith("junit-") ||
                    dependency.name.startsWith("hamcrest-core-") ||
                    dependency.name.startsWith("kotlin-stdlib-")
            }
        },
    )
}
