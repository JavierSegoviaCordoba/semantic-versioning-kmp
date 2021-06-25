# Module semantic-versioning-core

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

#### Passing a `version: String?` and a `stage: String?`

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
