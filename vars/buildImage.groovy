
def call(BUILD_IMAGE_NAME) {
    script {
        // Use the source command to run count_script.sh in the same shell
        TAG_ID = sh(script: '. ../imageTagId.sh', returnStdout: true).trim()

        // Use the new count in the docker build command
        sh "docker build -t ${env.BUILD_IMAGE_NAME}:${TAG_ID} ."
    }
}


