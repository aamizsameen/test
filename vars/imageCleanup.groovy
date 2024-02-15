def call(BUILD_IMAGE_NAME) {
	script {
        TAG_ID=sh(returnStdout: true, script: """cat ../vars/tagId.txt""").trim()
		sh "docker rmi -f ${env.BUILD_IMAGE_NAME}:${TAG_ID}"
	}
}


