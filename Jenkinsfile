    pipeline {
        agent any

        stages {

            stage('Build & Test') {
                steps {
                    sh '''
                           chmod +x mvnw
                           ./mvnw clean package
                        '''
                }
            }
        }
    }