# detekt

## Metrics

* 31 number of properties

* 51 number of functions

* 7 number of classes

* 2 number of packages

* 6 number of kt files

## Complexity Report

* 913 lines of code (loc)

* 811 source lines of code (sloc)

* 671 logical lines of code (lloc)

* 5 comment lines of code (cloc)

* 162 cyclomatic complexity (mcc)

* 48 cognitive complexity

* 2 number of total code smells

* 0% comment source ratio

* 241 mcc per 1,000 lloc

* 2 code smells per 1,000 lloc

## Findings (2)

### complexity, ComplexCondition (2)

Complex conditions should be simplified and extracted into well-named methods if necessary.

[Documentation](https://detekt.dev/docs/rules/complexity#complexcondition)

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:77:17
```
This condition is too complex (6). Defined complexity threshold for conditions is set to '4'
```
```kotlin
74     fun stage_name_comparator() = runTest {
75         forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
76             if (
77                 (a.major == b.major) &&
!!                 ^ error
78                     (a.minor == b.minor) &&
79                     (a.patch == b.patch) &&
80                     (a.stage?.name != null) &&

```

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:93:17
```
This condition is too complex (9). Defined complexity threshold for conditions is set to '4'
```
```kotlin
90     fun stage_num_comparator() = runTest {
91         forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
92             if (
93                 (a.major == b.major) &&
!!                 ^ error
94                     (a.minor == b.minor) &&
95                     (a.patch == b.patch) &&
96                     (a.stage?.name != null) &&

```

generated with [detekt version 1.21.0](https://detekt.dev/) on 2022-10-12 08:37:07 UTC
