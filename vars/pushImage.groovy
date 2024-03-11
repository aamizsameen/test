def call(REGION, REPO_LOGIN, REPO_URL, BUILD_IMAGE_NAME, DEPLOYMENT_NAME, NAMESPACE) {
        script {
		
                TAG_ID = sh(script: """
                       kubectl get deployment \${DEPLOYMENT_NAME} -n \${NAMESPACE} -o=jsonpath='{.spec.template.spec.containers[0].image}' | awk -F':' '{printf "%.1f", \$2 + 0.1}'
                """, returnStdout: true).trim()

                sh "aws ecr get-login-password --region ${env.REGION} | docker login --username AWS --password-stdin ${env.REPO_LOGIN}"
                sh "docker tag ${env.BUILD_IMAGE_NAME}:${TAG_ID} ${env.REPO_URL}:${TAG_ID}"
                sh "docker push ${env.REPO_URL}:${TAG_ID}"

        }
}
