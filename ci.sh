#!/bin/bash
# Please run it from root project directory

# This will: compile the project, run lint, run tests under JVM, package apk, check the code quality and run tests.
./gradlew clean :rxmvvm-task:build :rxmvvm-lce:build :rxmvvm-lce-component:build -PdisablePreDex