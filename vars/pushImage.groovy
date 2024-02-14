//The image built from buildImage is pushed to ECR with proper tag
def call(REGION, REPO_LOGIN, REPO_URL, BUILD_IMAGE_NAME) {
	script {
		//echo "$region, $ecr_repo, $service1_local_image, $service1_image, $service2_local_image, $service2_image"
        sh "aws ecr get-login-password --region ${env.REGION} | docker login --username AWS --password-stdin ${env.REPO_LOGIN}"
        sh "docker tag ${env.BUILD_IMAGE_NAME}:latest ${env.REPO_URL}:latest"
        sh "docker push ${env.REPO_URL}:latest"
	}
}
