import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import java.io.File

@OptIn(ExperimentalCompilerApi::class)
internal fun compilationForAssertations(
  sources: List<String>,
  assertAction: SymbolProcessorEnvironment.(Resolver) -> Unit,
): List<File> {
  assert(sources.any { it.isNotBlank() })
  return compile(sources.toSomeClasses(), assertAction)
}

@OptIn(ExperimentalCompilerApi::class)
private fun compile(
  sourceFiles: List<SourceFile>,
  assertAction: SymbolProcessorEnvironment.(Resolver) -> Unit,
): List<File> {
  assert(sourceFiles.isNotEmpty())
  val testKspProcessorProvider = TestKspProcessor.provider(assertAction)
  return compilation(
    sourceFiles = sourceFiles,
    processorProvider = testKspProcessorProvider,
  )
    .compile()
    .also {
      if (KotlinCompilation.ExitCode.OK == it.exitCode) return emptyList()
      error(it.messages)
    }
    .generatedFiles.toList()
}

internal fun List<String>.toSomeClasses(): List<SourceFile> = mapIndexed { index, name ->
  name.toSomeClass(
    index,
  )
}

internal fun compilationForAssertations(
  source: File,
  assertAction: SymbolProcessorEnvironment.(Resolver) -> Unit,
): List<File> = compile(listOf(SourceFile.fromPath(source)), assertAction)

private fun String.toSomeClass(index: Int) = SourceFile.kotlin("SomeClass$index.kt", this)

@OptIn(ExperimentalCompilerApi::class)
internal fun compilation(
  sourceFiles: List<SourceFile>,
  processorProvider: SymbolProcessorProvider,
) = KotlinCompilation().apply {
  sources = sourceFiles
  symbolProcessorProviders = listOf(processorProvider)
  inheritClassPath = true
}
