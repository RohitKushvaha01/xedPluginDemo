plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.rk.jvm"
    compileSdk = 34
    
    
    
    sourceSets {
        getByName("main") {
            jniLibs.srcDirs("src/main/jniLibs")
        }
    }

    defaultConfig {
        applicationId = "com.rk.jvm"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    //implementation(libs.material)?.let { runtimeOnly(it) }
   // implementation(platform("io.github.Rosemoe.sora-editor:bom:0.23.4"))?.let { runtimeOnly(it) }
    //implementation("io.github.Rosemoe.sora-editor:editor")?.let { runtimeOnly(it) }
    //implementation("org.eclipse.lsp4j:org.eclipse.lsp4j:0.22.0")?.let { runtimeOnly(it) }
    implementation(files("../libs/xedPlugin.aar"))
    compileOnly(libs.material)
    compileOnly(platform("io.github.Rosemoe.sora-editor:bom:0.23.4"))
    compileOnly("io.github.Rosemoe.sora-editor:editor")
    compileOnly("org.eclipse.lsp4j:org.eclipse.lsp4j:0.22.0")
    
    
}