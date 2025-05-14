![Maven Central](https://img.shields.io/maven-central/v/io.github.antonbutov/compilation)

## Setup

```
  repositories {
    mavenCentral()
  }
```
```
  implementation("com.github.antonbutov:compilation:<$last-version>")
```
## How to use
```
  val correctCode =
    """
        class Test {
            fun test(): String {
                return "test"
            }
        }
    """.trimIndent()

  @Test
  fun `correct code should compile`() {
    correctCode compile { resolver: Resolver ->
      assertTrue { true }
    }
  }

  @Test
  fun `correct file should compile`() {
    File("src/test/kotlin/FilesForTests/CorrectFile.kt") compile {
      assertTrue { true }
    }
  }
```
