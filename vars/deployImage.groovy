def call(DEPLOYMENT_NAME, REPO_URL, NAMESPACE, CONTAINER_NAME) {
        script {
                TAG_ID = sh(script: """
                       kubectl get deployment \${DEPLOYMENT_NAME} -n \${NAMESPACE} -o=jsonpath='{.spec.template.spec.containers[0].image}' | awk -F':' '{printf "%.1f", \$2 + 0.1}'
                """, returnStdout: true).trim()
                sh "kubectl set image deployment/${env.DEPLOYMENT_NAME} ${env.CONTAINER_NAME}=${env.REPO_URL}:${TAG_ID} -n ${env.NAMESPACE}"
                sh "kubectl rollout restart deployment/${env.DEPLOYMENT_NAME} -n ${env.NAMESPACE}"
        }
}
