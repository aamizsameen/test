//Using the newly created Task Definition we create a new deployment
def call(DEPLOYMENT_NAME, REPO_URL, NAMESPACE, CONTAINER_NAME) {
	script {
		sh "kubectl set image deployment/${env.DEPLOYMENT_NAME} ${env.CONTAINER_NAME}=${env.REPO_URL}:latest -n ${env.NAMESPACE}"
		sh "kubectl rollout restart deployment/${env.DEPLOYMENT_NAME} -n ${env.NAMESPACE}"
	}
}


