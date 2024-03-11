def call(BUILD_IMAGE_NAME) {
        script {
                sh "docker system prune -af"
        }
}
