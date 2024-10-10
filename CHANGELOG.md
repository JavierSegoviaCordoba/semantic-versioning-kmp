# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Fixed

### Removed

### Updated

- `com.javiersc.hubdle:com.javiersc.hubdle.gradle.plugin -> 0.7.14`
- `gradle -> 8.10.2`

## [0.1.0-beta.13] - 2023-01-25

### Updated

- `kotlin -> 1.8.0`
- `com.javiersc.hubdle:com.javiersc.hubdle.gradle.plugin -> 0.4.0-alpha.4`

## [0.1.0-beta.12] - 2023-01-04

### Added

- `Version.isAlpha: Boolean` extension
- `Version.isNotAlpha: Boolean` extension
- `String.isAlpha: Boolean` extension
- `String.isNotAlpha: Boolean` extension
- `Version.isBeta: Boolean` extension
- `Version.isNotBeta: Boolean` extension
- `String.isBeta: Boolean` extension
- `String.isNotBeta: Boolean` extension
- `Version.isDev: Boolean` extension
- `Version.isNotDev: Boolean` extension
- `String.isDev: Boolean` extension
- `String.isNotDev: Boolean` extension
- `Version.isRC: Boolean` extension
- `Version.isNotRC: Boolean` extension
- `String.isRC: Boolean` extension
- `String.isNotRC: Boolean` extension
- `Version.isSnapshot: Boolean` extension
- `Version.isNotSnapshot: Boolean` extension
- `String.isSnapshot: Boolean` extension
- `String.isNotSnapshot: Boolean` extension

## [0.1.0-beta.11] - 2023-01-04

### Changed

- `SNAPSHOT` is treated now as non-special stage (higher than `rc`)

### Removed

- `nextSnapshotMajor` function
- `nextSnapshotMinor` function
- `nextSnapshotPatch` function

### Updated

- `gradle -> 7.6`
- `com.javiersc.hubdle:com.javiersc.hubdle.gradle.plugin -> 0.2.0-alpha.46`

## [0.1.0-beta.10] - 2021-12-18

### Updated

- `gradle -> 7.3.2`
- `org.jetbrains.kotlin:kotlin-gradle-plugin -> 1.6.10`

## [0.1.0-beta.9] - 2021-12-13

### Fixed

- `num` is not being reset when `number` is provided to `inc` function

### Updated

- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.24`
- `io.kotest:kotest-assertions-core -> 5.0.2`
- `io.kotest:kotest-property -> 5.0.1`
- `gradle -> 7.3.1`

## [0.1.0-beta.8] - 2021-11-30

### Updated

- `io.kotest:kotest-assertions-core -> 5.0.0`
- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.19`
- `com.javiersc.run-blocking:suspend-test -> 0.1.0-beta.2`

## [0.1.0-beta.7] - 2021-11-21

### Changed

- artifact to `com.javiersc.semver:semver-core`
- package structure to `com.javiersc.semver`

### Updated

- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.12`
- `org.jetbrains.kotlin:kotlin-gradle-plugin -> 1.6.0`

## [0.1.0-beta.6] - 2021-11-11

### Removed

- support of versions with no patch

### Updated

- `gradle -> 7.3`
- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.8`
- `org.jetbrains.kotlinx:binary-compatibility-validator -> 0.8.0`

## [0.1.0-beta.5] - 2021-11-08

### Changed

- `fun inc` accepts `stageName`

### Removed

- `num` from `Version.Increase`

## [0.1.0-beta.4] - 2021-11-08

### Added

- stage name support to `fun inc`
- `fun nextSnapshotPatch` to get the next snapshot based on the patch
- `fun nextSnapshotMinor` to get the next snapshot based on the minor
- `fun nextSnapshotMajor` to get the next snapshot based on the major

## [0.1.0-beta.3] - 2021-11-07

### Added

- `safe` functions to every type of `invoke` function

## [0.1.0-beta.2] - 2021-11-07

### Added

- `safe` function which returns `Result<Version>` instead of crashing

### Updated

- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.4`

## [0.1.0-beta.1] - 2021-11-01

### Added

- `inc` function to increase the `Version`
- `copy` function to create a new `Version` based on another one

### Changed

- `SNAPSHOT` feature respects alphabetical order

### Updated

- `com.javiersc.gradle-plugins:versioning -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:readme-badges -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:publish-kotlin-multiplatform -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:plugin-accessors -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:nexus -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:kotlin-multiplatform-no-android -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:docs -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:code-formatter -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:code-analysis -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:changelog -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:all-projects -> 0.1.0-alpha.71`

## [0.1.0-alpha.4] - 2021-08-31

### Added

- `SNAPSHOT` versions are higher than non-final versions if they have the same `major`, `minor` and
  `patch`

### Fixed

- versions with same `major` and `minor` but with `patch = null` compared to `patch = 0` are not 
  equals

## [0.1.0-alpha.3] - 2021-06-28

### Changed

- override `toString` in both, `Version` and `Stage`

## [0.1.0-alpha.2] - 2021-06-27

### Changed

- Version `value` visibility from `private` to `public`
- Version `regex` visibility from `private` to `public`
- Stage `regex` visibility from `private` to `public`

## [0.1.0-alpha.1] - 2021-06-25

- No changes

[Unreleased]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-beta.13...HEAD

[0.1.0-beta.13]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-beta.12...0.1.0-beta.13

[0.1.0-beta.12]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-beta.11...0.1.0-beta.12

[0.1.0-beta.11]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-beta.10...0.1.0-beta.11

[0.1.0-beta.10]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-beta.9...0.1.0-beta.10

[0.1.0-beta.9]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-beta.8...0.1.0-beta.9

[0.1.0-beta.8]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-beta.7...0.1.0-beta.8

[0.1.0-beta.7]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-beta.6...0.1.0-beta.7

[0.1.0-beta.6]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-beta.5...0.1.0-beta.6

[0.1.0-beta.5]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-beta.4...0.1.0-beta.5

[0.1.0-beta.4]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-beta.3...0.1.0-beta.4

[0.1.0-beta.3]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-beta.2...0.1.0-beta.3

[0.1.0-beta.2]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-beta.1...0.1.0-beta.2

[0.1.0-beta.1]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-alpha.4...0.1.0-beta.1

[0.1.0-alpha.4]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-alpha.3...0.1.0-alpha.4

[0.1.0-alpha.3]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-alpha.2...0.1.0-alpha.3

[0.1.0-alpha.2]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/compare/0.1.0-alpha.1...0.1.0-alpha.2

[0.1.0-alpha.1]: https://github.com/JavierSegoviaCordoba/semantic-versioning-kmp/commits/0.1.0-alpha.1
