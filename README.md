![Kotlin version](https://img.shields.io/badge/kotlin-1.5.31-blueviolet?logo=kotlin&logoColor=white)
[![MavenCentral](https://img.shields.io/maven-central/v/com.javiersc.semantic-versioning/semantic-versioning-core?label=MavenCentral)](https://repo1.maven.org/maven2/com/javiersc/semantic-versioning/semantic-versioning-core/)
[![Snapshot](https://img.shields.io/nexus/s/com.javiersc.semantic-versioning/semantic-versioning-core?server=https%3A%2F%2Foss.sonatype.org%2F&label=Snapshot)](https://oss.sonatype.org/content/repositories/snapshots/com/javiersc/semantic-versioning/semantic-versioning-core/)

[![Build](https://img.shields.io/github/workflow/status/JavierSegoviaCordoba/semantic-versioning-kmp/build?label=Build&logo=GitHub)](https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/tree/main)
[![Quality](https://img.shields.io/sonar/quality_gate/JavierSegoviaCordoba_semantic-versioning-kmp?label=Quality&logo=SonarCloud&logoColor=white&server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=JavierSegoviaCordoba_semantic-versioning-kmp)
[![Tech debt](https://img.shields.io/sonar/tech_debt/JavierSegoviaCordoba_semantic-versioning-kmp?label=Tech%20debt&logo=SonarCloud&logoColor=white&server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=JavierSegoviaCordoba_semantic-versioning-kmp)

# SEMANTIC VERSIONING KMP

A Kotlin Multiplatform library to work with semantic versioning

## Download

```kotlin
implementation("com.javiersc.semantic-versioning:semantic-versioning-core:$version")
```

## Usage

A Version must match the following format:

```
<major>.<minor>.<patch?>-<stage?.name>.<stage?.num>
```

> `patch` and `stage` can be null.

### Build a `Version`

There are 3 options to build a `Version`.

#### Passing a `version: String`

```kotlin
Version("1.0")

Version("1.0.0")

Version("1.0.0-alpha.1")

Version("12.23.34-alpha.45")
``` 

#### Passing a `version: String` and a `stage: String?`

```kotlin
Version("1.0", "alpha.1")

Version("1.0.0", "alpha.1")

Version("12.23.34", "alpha.45")
``` 

#### Passing all params; `major: Int`, `minor: Int`, `patch: Int?`, `stage: String?`, and `num: Int?`

```kotlin
Version(1, 0)

Version(1, 0, 0)

Version(1, 0, 0, "alpha", 1)

Version(12, 23, 34, "alpha", 45)
```

### Compare two `Version`

```kotlin
Version("1.0.1") > Version("1.0.0") // true 

Version("1.0.1") < Version("1.0.0") // false

Version("1.0.0") == Version("1.0.0") // true
```

### Increase a `Version`

```kotlin
Version("2.4.6-alpha.1").inc(Version.Increase.Patch) // 2.4.7 (stage and num are removed)
Version("2.4.6").inc(Version.Increase.Patch) // 2.4.7
Version("2.4.6").inc(Version.Increase.Minor) // 2.5.0 (patch is reset to 0)
Version("2.4.6").inc(Version.Increase.Major) // 3.0.0 (minor and patch are reset to 0)

// minor and patch are reset to 0, stage and num are removed
Version("2.4.6-beta.4").inc(Version.Increase.Major) // 3.0.0
```

### Copy a `Version`

```kotlin
Version("1.1.0-alpha.1").copy(major = 3) // 3.1.0-alpha.1
```
