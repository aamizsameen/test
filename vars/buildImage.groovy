//Latest image is build from the latest clone from pullGitRepo.groovy
def call(BUILD_IMAGE_NAME) {
  sh TAG_ID = ". imageTagId.sh"
  sh "docker build -t ${env.BUILD_IMAGE_NAME}:$TAG_ID ."
}

