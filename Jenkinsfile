pipeline {
  agent any
  stages {
    stage('Build Docker Image') {
      steps {
        sh 'docker build -t etreservation .'
      }
    }

  }
  environment {
    FIREFOX_VERSION = '78.0.2'
    FIREFOXDRIVER_VERSION = '0.26.0'
  }
}