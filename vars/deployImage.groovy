//Using the newly created Task Definition we create a new deployment
def call(DEPLOYMENT_NAME, REPO_URL, NAMESPACE, CONTAINER_NAME) {
	script {
        TAG_ID=sh(returnStdout: true, script: """cat ../vars/tagId.txt""").trim()
		sh "kubectl set image deployment/${env.DEPLOYMENT_NAME} ${env.CONTAINER_NAME}=${env.REPO_URL}:${TAG_ID} -n ${env.NAMESPACE}"
		sh "kubectl rollout restart deployment/${env.DEPLOYMENT_NAME} -n ${env.NAMESPACE}"
	}
}


