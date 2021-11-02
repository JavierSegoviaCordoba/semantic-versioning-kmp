# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

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
