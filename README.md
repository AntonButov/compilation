[![](https://jitpack.io/v/AntonButov/Compilation.svg)](https://jitpack.io/#AntonButov/Compilation)

## Setup

```
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
		maven { url = uri("https://jitpack.io") }
		}
	}

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
```
