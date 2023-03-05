# detekt

## Metrics

* 59 number of properties

* 48 number of functions

* 7 number of classes

* 2 number of packages

* 7 number of kt files

## Complexity Report

* 1,153 lines of code (loc)

* 1,025 source lines of code (sloc)

* 861 logical lines of code (lloc)

* 4 comment lines of code (cloc)

* 180 cyclomatic complexity (mcc)

* 70 cognitive complexity

* 5 number of total code smells

* 0% comment source ratio

* 209 mcc per 1,000 lloc

* 5 code smells per 1,000 lloc

## Findings (5)

### complexity, ComplexCondition (2)

Complex conditions should be simplified and extracted into well-named methods if necessary.

[Documentation](https://detekt.dev/docs/rules/complexity#complexcondition)

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:103:17
```
This condition is too complex (6). Defined complexity threshold for conditions is set to '4'
```
```kotlin
100     fun stage_name_comparator() = runTest {
101         forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
102             if (
103                 (a.major == b.major) &&
!!!                 ^ error
104                     (a.minor == b.minor) &&
105                     (a.patch == b.patch) &&
106                     (a.stage?.name != null) &&

```

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:132:17
```
This condition is too complex (9). Defined complexity threshold for conditions is set to '4'
```
```kotlin
129     fun stage_num_comparator() = runTest {
130         forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
131             if (
132                 (a.major == b.major) &&
!!!                 ^ error
133                     (a.minor == b.minor) &&
134                     (a.patch == b.patch) &&
135                     (a.stage?.name != null) &&

```

### complexity, CyclomaticComplexMethod (1)

Prefer splitting up complex methods into smaller, easier to test methods.

[Documentation](https://detekt.dev/docs/rules/complexity#cyclomaticcomplexmethod)

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:100:9
```
The function stage_name_comparator appears to be too complex based on Cyclomatic Complexity (complexity: 17). Defined complexity threshold for methods is set to '15'
```
```kotlin
97      }
98  
99      @Test
100     fun stage_name_comparator() = runTest {
!!!         ^ error
101         forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
102             if (
103                 (a.major == b.major) &&

```

### complexity, LargeClass (1)

One class should have one responsibility. Large classes tend to handle many things at once. Split up large classes into smaller classes that are easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#largeclass)

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:24:7
```
Class VersionTest is too large. Consider splitting it into smaller pieces.
```
```kotlin
21 import kotlin.test.Test
22 import kotlinx.coroutines.test.runTest
23 
24 class VersionTest {
!!       ^ error
25 
26     private val major = Arb.positiveInt(11)
27     private val minor = Arb.positiveInt(11)

```

### complexity, LongMethod (1)

One method should have one responsibility. Long methods tend to handle many things at once. Prefer smaller methods to make them easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#longmethod)

* semver-core/common/test/kotlin/com/javiersc/semver/VersionTest.kt:515:9
```
The function is_version is too long (156). The maximum length is 60.
```
```kotlin
512     }
513 
514     @Test
515     fun is_version() {
!!!         ^ error
516         Version("1.0.0").apply {
517             isAlpha.shouldBeFalse()
518             isNotAlpha.shouldBeTrue()

```

generated with [detekt version 1.22.0](https://detekt.dev/) on 2023-03-05 21:35:04 UTC
