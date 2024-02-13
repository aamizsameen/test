pipeline {
    agent any 

    environment {
    doError = '0'
    }
    
    stages {
        stage('Cloning') {
            steps {
                git branch: 'main', credentialsId: 'git', url: 'https://github.com/aamizsameen/AWS'
            }
        }
        stage('Build') {
            steps {
                script{
                    env.ImageName = input message: 'Enter the Docker image name',parameters: [string(defaultValue: 'test',description: 'Docker image name',name: 'IMAGE_NAME')]
                    env.TagId = input message: 'Enter the Docker tag id',parameters: [string(defaultValue: 'latest',description: 'Docker image tag',name: 'TAG_ID')]
                    sh "docker build -t ${env.ImageName}:${env.TagId} ."

                }
            }
        }
        stage('Pushing to ECR') {
            steps{  
                script {
                    sh "aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin 973903430757.dkr.ecr.ap-south-1.amazonaws.com"
                    sh "docker tag ${env.ImageName}:${env.TagId} 973903430757.dkr.ecr.ap-south-1.amazonaws.com/aamiz-repo:${env.TagId}"
                    sh "docker push 973903430757.dkr.ecr.ap-south-1.amazonaws.com/aamiz-repo:${env.TagId}"    
                }
            }
        }
        stage('Deployment') {
            steps {
                script {
                    sh "kubectl apply -f deploy.yaml"
                    sh "kubectl set image deployment/abc nginx=973903430757.dkr.ecr.ap-south-1.amazonaws.com/aamiz-repo:${env.TagId}"
                }
            }
        }

        stage('Cleanup') {
            steps {
                sh "docker rmi -f ${env.ImageName}:${env.TagId}"
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

---


pipeline{
    agent any 

    environment {
    doError = '0'
    GIT_REPO = "https://github.com/aamizsameen/AWS"
    GIT_BRANCH = "main"
    REPO_URL = "973903430757.dkr.ecr.ap-south-1.amazonaws.com/aamiz-repo"
    REPO_LOGIN = "973903430757.dkr.ecr.ap-south-1.amazonaws.com"
    DEPLOYMENT_NAME = "node_app"
    }

    stages {

        stage('Cloning CICD Repo') {
            steps {
                git branch: "${env.GIT_BRANCH}", credentialsId: 'git', url: "${env.GIT_REPO}"
            }
        }

        stage('Cloning Application Repo') {
            steps {
                git branch: "${env.GIT_BRANCH}", credentialsId: 'git', url: "${env.GIT_REPO}"
            }
        }

        stage("Build Image") {
        steps {
            script {
            try {
                timeout(time: 60, unit: 'SECONDS') {
                isBuild = input message: 'Do you want to make a new build?',
                            parameters: [[$class: 'ChoiceParameterDefinition', name: 'Do you want to make a new build?', choices: ['Yes', 'No'], description: 'Select yes if you would like to make new build']]
                }
            }
            catch (err) {
                echo "${err}"
                echo "isBuild passed as Yes"
                isBuild = "Yes"
            }
            if (isBuild == "Yes") {
                    env.ImageName = input message: 'Enter the Docker image name',parameters: [string(defaultValue: 'test',description: 'Docker image name',name: 'IMAGE_NAME')]
                    env.TagId = input message: 'Enter the Docker tag id',parameters: [string(defaultValue: 'latest',description: 'Docker image tag',name: 'TAG_ID')]
                    sh "docker build -t ${env.ImageName}:${env.TagId} ."
            }
            else {
                echo "Skipping build image"
            }
            }
        }
        }
        stage("Push Image") {
        steps {
            script {
            try {
                timeout(time: 60, unit: 'SECONDS') {
                isBuild = input message: "Do you want to push latest build? If you gave 'No' to build, please give 'No' here as well",
                            parameters: [[$class: 'ChoiceParameterDefinition', name: 'Do you want to Push the new image?', choices: ['Yes', 'No'], description: 'Select yes if you would like to make new build']]
                }
            }
            catch (err) {
                echo "${err}"
                echo "isBuild passed as Yes"
                isBuild = "Yes"
            }
            //This block tags the built image and pushes it to the ECR_REPO
                sh "aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin \"${env.REPO_LOGIN}\""
                sh "docker tag ${env.ImageName}:${env.TagId} \"${env.REPO_URL}\":${env.TagId}"
                sh "docker push \"${env.REPO_URL}\":${env.TagId}"    
            }
        }
        }
        stage("Deploy Latest Build") {
        steps {
            script {
            try {
                timeout(time: 60, unit: 'SECONDS') {
                isDeploy = input message: 'DeployLatestBuild',
                            parameters: [[$class: 'ChoiceParameterDefinition', name: 'Would you like to deploy the latest build right away?', choices: ['Yes', 'No'], description: 'Select yes if you would like to deploy the latest build']]
                }
            }
            catch(err) {
                echo "${err}"
                echo "isDeploy passed as Yes"
                isDeploy = "Yes"
            }
            if (isDeploy == "Yes") {

                sh "kubectl apply -f deploy.yaml"
                sh "kubectl set image deployment/\"${env.DEPLOYMENT_NAME}\" \"${env.REPO_URL}\":${env.TagId}"

            }
            }
        }
        }
        // stage("Deploy Different Build") {
        // steps {
        //     script {
        //     try {
        //         timeout(time: 60, unit: 'SECONDS') {
        //         isUseOldTaskDefinition = input message: 'Do you want to deploy a different task definition?',
        //                     parameters: [[$class: 'ChoiceParameterDefinition', name: 'Do you want to deploy a different task definition?', choices: ['Yes', 'No'], description: 'Select yes if you would like to deploy a different task definition?']]
        //         }
        //     }
        //     catch(err) {
        //         echo "${err}"
        //         echo "isUseOldTaskDefinition passed as No"
        //         isUseOldTaskDefinition = "No"
        //     }
        //     if (isUseOldTaskDefinition == "Yes") {
        //         try {
        //         timeout(time: 60, unit: 'SECONDS') {
        //             taskNumber = input message: 'Provide the task revision number',
        //                         parameters: [string(name: 'TaskDefinition Number', description: "Please enter the build number")]
        //             oldTaskDefinition = "${env.FAMILY}:${taskNumber}"
        //             echo "$oldTaskDefinition"
        //         }
        //         }
        //         catch(err) {
        //         echo "${err}"
        //         echo "TaskNumber cannot be empty and there is no default task number set, requires an user input"
        //         }
        //         try {
        //         timeout(time: 60, unit: 'SECONDS') {
        //             desiredCount = input message: "DesiredNumberOfConcurrentServices",
        //                     parameters: [[$class: 'ChoiceParameterDefinition', choices: ["1", "2", "3", "4"],
        //                             description: 'Please provide the desired number of services you would like to run',
        //                             name: 'DesiredCount'
        //                             ]]
        //         }
        //         deployImage("${env.CLUSTER}","${env.SERVICE}", "${env.REGION}", "$oldTaskDefinition", "$desiredCount")
        //         }
        //         catch(err) {
        //         echo "${err}"
        //         echo "No. of services passed as 1"
        //         desiredCount = "1"
        //         deployImage("${env.CLUSTER}","${env.SERVICE}", "${env.REGION}", "$oldTaskDefinition", "$desiredCount")
        //         }  
        //     }
        //     }
        // }
        // }
    }
}