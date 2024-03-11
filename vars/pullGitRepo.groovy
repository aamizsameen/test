def call(branch, credentialsId, repoUrl) {
    git branch: branch, credentialsId: credentialsId, url: repoUrl
}
