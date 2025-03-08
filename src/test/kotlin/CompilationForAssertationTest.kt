import api.compile
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertTrue

class CompilationForAssertationTest {
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
    correctCode compile {
      assertTrue { true }
    }
  }

  @Test
  fun `incorrect code should not compile`() {
    assertThrows<IllegalStateException> {
      correctCode compile {
        error("My error.")
      }
    }
  }

  @Test
  fun `blank sourse should trow error`() {
    assertThrows<AssertionError> {
      "" compile {
        assertTrue { true }
      }
    }
  }

  val incorrectCode =
    """
        bla bla bla
    """.trimIndent()

  @Test
  fun `incorrect code should not compile with custom message`() {
    assertThrows<IllegalStateException> {
      incorrectCode compile {}
    }
  }
}
