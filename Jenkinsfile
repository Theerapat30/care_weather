pipeline {
    agent any
    tools {
        gradle 'gradle-8.14'
    }

    stages {
        stage('Build'){
            steps {
                echo 'Building..'
            }
        }
        stage('Test'){
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy'){
            steps {
                echo 'Deploying..'
            }
        }
    }

    post {
        always {
            echo 'Alway say good bye!'
        }
    }
}