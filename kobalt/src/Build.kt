import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.application.*
import com.beust.kobalt.plugin.kotlin.*

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
        compile("com.weblookandfeel:weblaf-ui:1.2.8")
        compile("com.beust:klaxon:0.24")
        compile("com.github.insubstantial:substance:7.3")
        compile("org.pegdown:pegdown:1.6.0")
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
