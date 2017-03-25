node {
    try {
        notifyBuild('STARTED')
        def project = 'fi-android'
        def appName = 'DS FieldInsight'

        stage('Checkout') {
            checkout scm
        }

        stage('Build Image') {
            sh("docker build -t ${project} .")
        }

        stage('Run application test') {
            sh("env >> .env")
            sh("docker run --env-file .env --rm ${project} ./gradlew test")
        }

        stage('Deploy application') {
            switch (env.BRANCH_NAME) {
                case "master":
                    sh("env >> .env")
                    sh("docker run --env-file .env --rm ${project} ./gradlew clean build assembleRelease crashlyticsUploadDistributionRelease")
                    break
                case "dev":
                    sh("env >> .env")
                    sh("docker run --env-file .env --rm ${project} ./gradlew clean build assembleDebug crashlyticsUploadDistributionDebug")
                    break
            }
        }
    } catch (e) {
        currentBuild.result = "FAILED"
        throw e
      } finally {
        notifyBuild(currentBuild.result)
    }
}

def notifyBuild(String buildStatus = 'STARTED') {
  buildStatus =  buildStatus ?: 'SUCCESSFUL'

  def color = 'RED'
  def colorCode = '#FF0000'
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  def summary = "${subject} (${env.BUILD_URL})"
  def details = """<p>STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""

  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFCC00'
  } else if (buildStatus == 'SUCCESSFUL') {
    color = 'GREEN'
    colorCode = '#228B22'
  } else {
    color = 'RED'
    colorCode = '#FF0000'
  }

  slackSend (color: colorCode, message: summary)
}