import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import java.io.File

infix fun String.compile(action: SymbolProcessorEnvironment.(Resolver) -> Unit): List<File> {
  val sources = listOf(this)
  return compilationForAssertations(sources, action)
}

infix fun List<String>.compile(action: SymbolProcessorEnvironment.(Resolver) -> Unit): List<File> {
  return compilationForAssertations(this, action)
}

infix fun File.compile(action: SymbolProcessorEnvironment.(Resolver) -> Unit): List<File> {
  if (exists()) {
    return compilationForAssertations(this, action)
  }
  throw IllegalArgumentException("File $this does not exist")
}
