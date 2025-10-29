plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.ksp)
	alias(libs.plugins.hilt)
}

android {
	namespace = "com.example.logos"
	compileSdk = 35

	defaultConfig {
		applicationId = "com.example.logos"
		minSdk = 24
		targetSdk = 35
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
			excludes += "/META-INF/gradle/incremental.annotation.processors"
		}
	}
	composeOptions { kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get() }
}

dependencies {
	with(libs) {
		implementation(bundles.base)
		implementation(bundles.compose)
		implementation(bundles.coroutines)
		implementation(bundles.hilt)
		implementation(bundles.room)
		implementation(bundles.network)
		implementation(bundles.devtools)
		testImplementation(bundles.unit.test)
		ksp(hilt.processor)
		ksp(room.processor)
	}
}