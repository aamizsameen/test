//Clone the CICD and Application repo.

def call(CICD_GIT_BRANCH, CICD_CREDENTIALS_ID, GIT_REPO) {
  git branch: CICD_GIT_BRANCH, credentialsId: CICD_CREDENTIALS_ID, url: GIT_REPO
}