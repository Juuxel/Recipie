import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.application.*

val repos = repos()

val p = project {
    name = "Recipie"
    group = "juuxel.recipie"
    artifactId = name
    version = "0.1.0"

    sourceDirectories {
        path("src/main/kotlin")
        path("src/main/resources")
    }

    sourceDirectoriesTest {
        /*path("src/test/kotlin")*/
    }

    dependencies {
        compile("com.weblookandfeel:weblaf-ui:")
        compile("com.beust:klaxon:")
        compile("com.github.insubstantial:substance:")
        compile("org.pegdown:pegdown:")
    }

    dependenciesTest {}

    assemble {
        jar {
            manifest {
                attributes("Main-Class", "juuxel.recipie.MainKt")
            }
        }
    }

    application {
        mainClass = "juuxel.recipie.MainKt"
    }
}
