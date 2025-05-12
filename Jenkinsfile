pipeline {
    agent any
    environment {
        ANDROID_HOME = "/home/jenkins/sdk/android"
    }

    tools {
        gradle 'gradle-8.14'
        jdk 'java-21'
    }


    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'main', credentialsId: 'jenkins-github-trp', url: 'https://github.com/Theerapat30/care_weather.git'
                sh 'cp /home/jenkins/projects/android/care-weather-android/keystore.properties $WORKSPACE'
                sh './gradlew clean'
                sh './gradlew app:bundleRelease'
                // archiveArtifacts artifacts: 'app/build/outputs/bundle/release/*.aab', followSymlinks: false, allowEmptyArchive: false
            }
        }
        stage('Deploy') {
            steps {
                androidApkUpload filesPattern: 'app/build/outputs/bundle/release/*.aab', googleCredentialsId: 'jenkins-service-account', releaseName: '{versionName}', rolloutPercentage: '0', trackName: 'internal'
            }
        }
    }
}
