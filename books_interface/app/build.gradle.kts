plugins {
    id("com.android.application")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

}


android {
    namespace = "com.example.books_interface"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.books_interface"
        minSdk = 24

        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

        targetCompatibility = JavaVersion.VERSION_1_8


    }
    buildToolsVersion = "34.0.0"
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("com.sothree.slidinguppanel:library:3.4.0")
    implementation ("mysql:mysql-connector-java:8.0.33")
   }
