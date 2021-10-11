# Changelog

## [Unreleased]

### Added
- `inc` function to increase the `Version`
- `copy` function to create a new `Version` based on another one

### Changed
- `SNAPSHOT` feature respects alphabetical order

### Deprecated

### Removed

### Fixed

### Updated


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
