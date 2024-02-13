//Latest image is build from the latest clone from pullGitRepo.groovy
def call(BUILD_IMAGE_NAME, TAG_ID) {
  sh "docker build -t ${env.BUILD_IMAGE_NAME}:${env.TAG_ID} ."
}


