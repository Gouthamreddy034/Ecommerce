pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        git 'https://github.com/Gouthamreddy034/Ecommerce.git'
      }
    }

    stage('Build') {
      steps {
        sh 'mvn clean install'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }

  }
}