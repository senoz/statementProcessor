pipeline {
  environment {
    registry = "shenoz/myrepo"
    registryCredential = 'docker-hub-credentials'
    dockerImage = ''
  }
  agent any
  stages {
    stage('Cloning Git') {
      steps {
        git 'https://github.com/senoz/statementProcessor.git'
      }
    }
    stage('Compile Stage') {
          steps {
              withMaven(
                  // Maven installation declared in the Jenkins "Global Tool Configuration"
                  maven: 'maven-3',
                  // Maven settings.xml file defined with the Jenkins Config File Provider Plugin
                  // We recommend to define Maven settings.xml globally at the folder level using 
                  // navigating to the folder configuration in the section "Pipeline Maven Configuration / Override global Maven configuration"
                  // or globally to the entire master navigating to  "Manage Jenkins / Global Tools Configuration"
                  mavenSettingsConfig: 'my-maven-settings') {
                  bat'mvn clean compile'
              }
          }
      }
      stage('Testing Stage') {
          steps {
              withMaven(
                  // Maven installation declared in the Jenkins "Global Tool Configuration"
                  maven: 'maven-3',
                  // Maven settings.xml file defined with the Jenkins Config File Provider Plugin
                  // We recommend to define Maven settings.xml globally at the folder level using 
                  // navigating to the folder configuration in the section "Pipeline Maven Configuration / Override global Maven configuration"
                  // or globally to the entire master navigating to  "Manage Jenkins / Global Tools Configuration"
                  mavenSettingsConfig: 'my-maven-settings') {
                  bat'mvn test'
              }
          }
      }
      stage('Install Stage') {
          steps {
              withMaven(
                  // Maven installation declared in the Jenkins "Global Tool Configuration"
                  maven: 'maven-3',
                  // Maven settings.xml file defined with the Jenkins Config File Provider Plugin
                  // We recommend to define Maven settings.xml globally at the folder level using 
                  // navigating to the folder configuration in the section "Pipeline Maven Configuration / Override global Maven configuration"
                  // or globally to the entire master navigating to  "Manage Jenkins / Global Tools Configuration"
                  mavenSettingsConfig: 'my-maven-settings') {
                  bat'mvn install'
              }
          }
      }
    /*
    stage('Building image') {
      steps{
        script {
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }
      }
    }
    stage('Push Image') {
      steps{
        script {
          // Finally, we'll push the image with two tags:
            //       * First, the incremental build number from Jenkins
            //      * Second, the 'latest' tag.
            //      * Pushing multiple tags is cheap, as all the layers are reused. 
          docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
              dockerImage.push("${env.BUILD_NUMBER}")
              dockerImage.push("latest")
          }
        }
      }
    }
    stage('Deploy to K8S'){
        steps{
            sh 'kubectl apply -f deployment.yml'
       }
    } */
  }
}
