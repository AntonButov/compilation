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
