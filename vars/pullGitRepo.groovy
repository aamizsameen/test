//Clone the CICD and Application repo.

def call(branch_name, credentialsId, GIT_REPO) {
  git branch: branch_name, credentialsId: credentialsId, url: GIT_REPO
}