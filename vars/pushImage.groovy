//The image built from buildImage is pushed to ECR with proper tag
def call(REGION, REPO_LOGIN, REPO_URL, BUILD_IMAGE_NAME) {
	script {
	
	//def FILE = '../vars/tagId.txt'

	//def TAG_ID = new File(FILE).text.trim()

	//sh TAG_ID="cat ../vars/tagId.txt"


	//def countFile = '../vars/tagId.txt'

	// Read the contents of the file into a variable
	//def TAG_ID = new File(countFile).text.trim()

	// Print the tag ID variable
	
	//println "Tag ID Variable: $tagId"

	//sh "TAG_ID=$(cat ../vars/tagId.txt)"
	sh 'TAG_ID=\\$(cat ../vars/tagId.txt)'

	//TAG_ID = new File(../vars/tagId.txt).text.trim()
        sh "aws ecr get-login-password --region ${env.REGION} | docker login --username AWS --password-stdin ${env.REPO_LOGIN}"
        sh "docker tag ${env.BUILD_IMAGE_NAME}:${TAG_ID} ${env.REPO_URL}:${TAG_ID}"
        sh "docker push ${env.REPO_URL}:${TAG_ID}"

	}
}
