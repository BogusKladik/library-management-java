pipeline {
  agent any
  tools { jdk 'JDK23'; maven 'Maven3' }
  environment { COVERAGE_THRESHOLD = '60' }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Compile') {
      steps {
        sh 'mvn clean compile'
        sh 'mvn test-compile'
      }
    }

    stage('Test') {
      when { branch 'feature/*' }
      steps {
        sh 'mvn verify'
      }
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
          archiveArtifacts artifacts: '**/target/site/jacoco/**', allowEmptyArchive: true
          jacoco (
            execPattern: '**/target/jacoco.exec',
            classPattern: '**/target/classes',
            sourcePattern: '**/src',
            minimumLineCoverage: COVERAGE_THRESHOLD,
            changeBuildStatus: true
          )
        }
      }
    }

    stage('Static Analysis') {
      when { branch 'develop' }
      steps {
        sh 'mvn pmd:check'
      }
    }

    stage('Install') {
      when { branch 'develop' }
      steps {
        sh 'mvn install -DskipTests'
      }
      post {
        success {
          echo 'Installed to local repo'
        }
      }
    }

    stage('Package App') {
      steps {
        sh 'mvn package -DskipTests'
      }
      post {
        success {
          archiveArtifacts artifacts: 'app/target/*jar-with-dependencies.jar', fingerprint: true
          sh 'cp app/target/*jar-with-dependencies.jar /Users/user/Projects/Java/deploy'
        }
      }
    }
  }

  post {
    success { echo 'Pipeline completed successfully!' }
    failure { echo 'Pipeline failed!' }
  }
}
