pipeline {
  agent any
  stages {
    stage('Build Docker Image') {
      steps {
        withMaven {
      sh "mvn clean verify"
    } // withMaven will discover the generated Maven artifacts, JUnit Surefire & FailSafe reports and FindBugs reports
      }
    }

  }
}
