# detekt

## Metrics

* 52 number of properties

* 48 number of functions

* 7 number of classes

* 2 number of packages

* 7 number of kt files

## Complexity Report

* 1,099 lines of code (loc)

* 974 source lines of code (sloc)

* 825 logical lines of code (lloc)

* 4 comment lines of code (cloc)

* 167 cyclomatic complexity (mcc)

* 59 cognitive complexity

* 3 number of total code smells

* 0% comment source ratio

* 202 mcc per 1,000 lloc

* 3 code smells per 1,000 lloc

## Findings (3)

### complexity, ComplexCondition (2)

Complex conditions should be simplified and extracted into well-named methods if necessary.

[Documentation](https://detekt.dev/docs/rules/complexity#complexcondition)

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:82:17
```
This condition is too complex (6). Defined complexity threshold for conditions is set to '4'
```
```kotlin
79     fun stage_name_comparator() = runTest {
80         forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
81             if (
82                 (a.major == b.major) &&
!!                 ^ error
83                     (a.minor == b.minor) &&
84                     (a.patch == b.patch) &&
85                     (a.stage?.name != null) &&

```

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:98:17
```
This condition is too complex (9). Defined complexity threshold for conditions is set to '4'
```
```kotlin
95      fun stage_num_comparator() = runTest {
96          forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
97              if (
98                  (a.major == b.major) &&
!!                  ^ error
99                      (a.minor == b.minor) &&
100                     (a.patch == b.patch) &&
101                     (a.stage?.name != null) &&

```

### complexity, LongMethod (1)

One method should have one responsibility. Long methods tend to handle many things at once. Prefer smaller methods to make them easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#longmethod)

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:481:9
```
The function is_version is too long (156). The maximum length is 60.
```
```kotlin
478     }
479 
480     @Test
481     fun is_version() {
!!!         ^ error
482         Version("1.0.0").apply {
483             isAlpha.shouldBeFalse()
484             isNotAlpha.shouldBeTrue()

```

generated with [detekt version 1.22.0](https://detekt.dev/) on 2023-01-04 15:56:17 UTC
