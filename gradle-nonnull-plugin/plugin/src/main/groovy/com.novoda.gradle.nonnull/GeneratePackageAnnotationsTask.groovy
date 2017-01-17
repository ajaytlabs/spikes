package com.novoda.gradle.nonnull

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.tasks.TaskAction

class GeneratePackageAnnotationsTask extends DefaultTask {

    def outputDir
    def sourceSets

    @TaskAction
    void generatePackageAnnotations() {
        description = "Annotates the source packages with @ParametersAreNonnullByDefault"

        Set<String> packages = packages()
        packages.each { packagePath ->
            def dir = new File(outputDir, packagePath)
            dir.mkdirs()

            def file = new File(dir, 'package-info.java')
            if (file.createNewFile()) {
                def packageName = packagePath.replaceFirst('/', '').replaceAll('/', '.')
                file.write(createAnnotationDefinition(packageName))
            }
        }
    }

    Set<String> packages() {
        Set<String> packages = []

        sourceSets.each { sourceSet ->
            sourceSet.java.srcDirs.findAll {
                it.exists()
            }.each { srcDir ->

                project.fileTree(srcDir).visit { FileVisitDetails details ->
                    if (details.file.file) {
                        def packagePath = details.file.parent.replace(srcDir.absolutePath, '')
                        packages << packagePath
                    }
                }
            }
        }
        packages
    }

    static def createAnnotationDefinition(packageName) {
        """ |/**
            | *
            | * Make all method parameters @NonNull by default.
            | *
            | * We assume that all method parameters are NON-NULL by default.
            | *
            | * e.g.
            | *
            | * void setValue(String value) {
            | *     this.value = value;
            | * }
            | *
            | * is equal to:
            | *
            | * void setValue(@NonNull String value) {
            | *     this.value = value;
            | * }
            | *
            | */
            |@ParametersAreNonnullByDefault
            |package ${packageName};
            |
            |import javax.annotation.ParametersAreNonnullByDefault;
            |""".stripMargin('|')
    }
}