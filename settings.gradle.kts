pluginManagement { repositories { google(); mavenCentral(); gradlePluginPortal() } }
dependencyResolutionManagement { repositories { google(); mavenCentral() } }
rootProject.name = "eChinese"
include(":app", ":core", ":domain", ":data", ":feature:auth")
