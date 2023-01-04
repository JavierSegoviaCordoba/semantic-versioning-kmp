# detekt

## Metrics

* 32 number of properties

* 47 number of functions

* 7 number of classes

* 2 number of packages

* 6 number of kt files

## Complexity Report

* 870 lines of code (loc)

* 772 source lines of code (sloc)

* 635 logical lines of code (lloc)

* 5 comment lines of code (cloc)

* 159 cyclomatic complexity (mcc)

* 59 cognitive complexity

* 2 number of total code smells

* 0% comment source ratio

* 250 mcc per 1,000 lloc

* 3 code smells per 1,000 lloc

## Findings (2)

### complexity, ComplexCondition (2)

Complex conditions should be simplified and extracted into well-named methods if necessary.

[Documentation](https://detekt.dev/docs/rules/complexity#complexcondition)

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:80:17
```
This condition is too complex (6). Defined complexity threshold for conditions is set to '4'
```
```kotlin
77     fun stage_name_comparator() = runTest {
78         forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
79             if (
80                 (a.major == b.major) &&
!!                 ^ error
81                     (a.minor == b.minor) &&
82                     (a.patch == b.patch) &&
83                     (a.stage?.name != null) &&

```

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:96:17
```
This condition is too complex (9). Defined complexity threshold for conditions is set to '4'
```
```kotlin
93      fun stage_num_comparator() = runTest {
94          forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
95              if (
96                  (a.major == b.major) &&
!!                  ^ error
97                      (a.minor == b.minor) &&
98                      (a.patch == b.patch) &&
99                      (a.stage?.name != null) &&

```

generated with [detekt version 1.22.0](https://detekt.dev/) on 2023-01-04 14:46:41 UTC
