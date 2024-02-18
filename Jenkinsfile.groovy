//@Library("deployScript@main") _
//Hello!!!
//Hello again!!


pipeline{
    agent any 

    environment {
    doError = '0'
    CICD_GIT_REPO = "https://github.com/aamizsameen/test.git"
    CICD_GIT_BRANCH = "main"
    CICD_CREDENTIALS_ID = "git"
    APP_GIT_REPO = "https://github.com/aamizsameen/AWS.git"
    APP_GIT_BRANCH = "main"
    APP_CREDENTIALS_ID = "git"
    BUILD_IMAGE_NAME = "nginx"
    REPO_URL = "058264316945.dkr.ecr.ap-south-1.amazonaws.com/test"
    REPO_LOGIN = "058264316945.dkr.ecr.ap-south-1.amazonaws.com"
    DEPLOYMENT_NAME = "nginx"
    REGION = "ap-south-1"
    NAMESPACE = "default"
    CONTAINER_NAME = "nginx"
    }


    stages {

        stage('Clone Application Repo') {
            steps {
                echo "Cloning App Repo..."
                pullGitRepo("${env.APP_GIT_BRANCH}", "${env.APP_CREDENTIALS_ID}", "${env.APP_GIT_REPO}")
                echo "App Repo Cloned."

            }
        }

        stage('Clone CICD Repo') {
            steps {
                dir('..') {

                    echo "Cloning CICD Repo..."
                    pullGitRepo("${env.CICD_GIT_BRANCH}", "${env.CICD_CREDENTIALS_ID}", "${env.CICD_GIT_REPO}")
                    echo "CICD Repo Cloned."   
                }
            }
        }

        stage("Build Image") {
            steps {
                echo "Building the latest image..."
                buildImage("${env.BUILD_IMAGE_NAME}")
            }
        }

        stage("Push Image") {
        steps {
            echo "Login into ECR Repo..."
            
            pushImage("${env.REGION}", "${env.REPO_LOGIN}", "${env.REPO_URL}", "${env.BUILD_IMAGE_NAME}")
            echo "Pushed the image to ECR"
            }
        }

        stage("Deploy Latest Build") {
        steps {
            echo "Deploying the latest build..."
            deployImage("${env.DEPLOYMENT_NAME}", "${env.REPO_URL}", "${env.NAMESPACE}", "${env.CONTAINER_NAME}")
            echo "Deployment is done"
            }
        }

        stage('Cleanup') {
            steps {
                echo "Cleaning up the images..."
                imageCleanup("${env.BUILD_IMAGE_NAME}")
                echo "Image cleanup completed"
            }
        }
        stage('Error') {
            // when doError is equal to 1, return an error
            when {
                expression { doError == '1' }
            }
            steps {
                echo "Failure :("
                error "Test failed on purpose, doError == str(1)"
            }
        }
        stage('Success') {
            // when doError is equal to 0, just print a simple message
            when {
                expression { doError == '0' }
            }
            steps {
                echo "Success :)"
            }
        }
    
    }
}
