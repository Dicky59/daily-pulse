import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.hilt.android)
  alias(libs.plugins.kotlin.kapt)
  alias(libs.plugins.ksp)
  alias(libs.plugins.ktlint)
}

android {
  namespace = "com.dicky59.dailypulse"
  compileSdk {
    version = release(36)
  }

  defaultConfig {
    applicationId = "com.dicky59.dailypulse"
    minSdk = 26
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    buildConfigField("String", "NEWS_API_KEY", "\"${properties.getProperty("NEWS_API_KEY")}\"")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
    }

    debug {
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
}

kapt {
  correctErrorTypes = true
}

ktlint {
  filter {
    exclude("**/NavigationRoutes.kt")
  }
}

dependencies {
  // Core Android
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)

  // Compose
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.material.icons.extended)

  // Navigation Compose
  implementation(libs.androidx.navigation.compose)
  implementation(libs.hilt.navigation.compose)

  // Hilt
  implementation(libs.hilt.android)
  kapt(libs.hilt.compiler)

  // Coroutines
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.coroutines.android)

  // Timber
  implementation(libs.timber)

  // Retrofit & OkHttp
  implementation(libs.retrofit)
  implementation(libs.retrofit.moshi)
  implementation(libs.okhttp)
  implementation(libs.okhttp.logging.interceptor)

  // Moshi
  implementation(libs.moshi)
  implementation(libs.moshi.kotlin)
  ksp(libs.moshi.kotlin.codegen)

  // Lifecycle ViewModel Compose
  implementation(libs.androidx.lifecycle.viewmodel.compose)

  // Coil
  implementation(libs.coil.compose)

  // Testing
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.tooling)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
}
