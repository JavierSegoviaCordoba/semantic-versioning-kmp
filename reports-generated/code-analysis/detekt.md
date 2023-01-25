# detekt

## Metrics

* 52 number of properties

* 48 number of functions

* 7 number of classes

* 2 number of packages

* 7 number of kt files

## Complexity Report

* 1,116 lines of code (loc)

* 991 source lines of code (sloc)

* 833 logical lines of code (lloc)

* 4 comment lines of code (cloc)

* 167 cyclomatic complexity (mcc)

* 59 cognitive complexity

* 3 number of total code smells

* 0% comment source ratio

* 200 mcc per 1,000 lloc

* 3 code smells per 1,000 lloc

## Findings (3)

### complexity, ComplexCondition (2)

Complex conditions should be simplified and extracted into well-named methods if necessary.

[Documentation](https://detekt.dev/docs/rules/complexity#complexcondition)

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:83:17
```
This condition is too complex (6). Defined complexity threshold for conditions is set to '4'
```
```kotlin
80     fun stage_name_comparator() = runTest {
81         forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
82             if (
83                 (a.major == b.major) &&
!!                 ^ error
84                     (a.minor == b.minor) &&
85                     (a.patch == b.patch) &&
86                     (a.stage?.name != null) &&

```

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:99:17
```
This condition is too complex (9). Defined complexity threshold for conditions is set to '4'
```
```kotlin
96      fun stage_num_comparator() = runTest {
97          forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
98              if (
99                  (a.major == b.major) &&
!!                  ^ error
100                     (a.minor == b.minor) &&
101                     (a.patch == b.patch) &&
102                     (a.stage?.name != null) &&

```

### complexity, LongMethod (1)

One method should have one responsibility. Long methods tend to handle many things at once. Prefer smaller methods to make them easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#longmethod)

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:482:9
```
The function is_version is too long (156). The maximum length is 60.
```
```kotlin
479     }
480 
481     @Test
482     fun is_version() {
!!!         ^ error
483         Version("1.0.0").apply {
484             isAlpha.shouldBeFalse()
485             isNotAlpha.shouldBeTrue()

```

generated with [detekt version 1.22.0](https://detekt.dev/) on 2023-01-25 13:18:19 UTC
