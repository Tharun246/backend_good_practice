pipeline {
    agent any

    stages {

        stage('Build & Test') {
            steps {
                sh './mvnw clean package'
            }
        }
    }
}