def call(BUILD_IMAGE_NAME, DEPLOYMENT_NAME, NAMESPACE) {
    script {
        TAG_ID = sh(script: """
            kubectl get deployment \${DEPLOYMENT_NAME} -n \${NAMESPACE} -o=jsonpath='{.spec.template.spec.containers[0].image}' | awk -F':' '{printf "%.1f", \$2 + 0.1}'
        """, returnStdout: true).trim()

        sh "docker build -t ${env.BUILD_IMAGE_NAME}:${TAG_ID} ."
    }
}
