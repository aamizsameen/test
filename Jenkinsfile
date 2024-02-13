
@Library("deployScript") _

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
    BUILD_IMAGE_NAME = "axcess-fe-v1"
    REPO_URL = "973903430757.dkr.ecr.ap-south-1.amazonaws.com/aamiz-repo"
    REPO_LOGIN = "973903430757.dkr.ecr.ap-south-1.amazonaws.com"
    DEPLOYMENT_NAME = "node_app"
    }


    stages {

        stage('CICD Repo') {
            steps {
                echo "Cloning CICD Repo..."
                pullGitRepo("${env.APP_GIT_BRANCH}", "${env.APP_CREDENTIALS_ID}", "${env.APP_GIT_REPO}")
                echo "CICD Repo Cloned."

                dir('..') {

                    echo "Cloning App Repo..."
                    pullGitRepo("${env.CICD_GIT_BRANCH}", "${env.CICD_CREDENTIALS_ID}", "${env.CICD_GIT_REPO}")
                    echo "App Repo Cloned."   
                }
            }
        }

        // stage('Application Repo') {
        //     steps {
         
        //     }
        // }

        stage("Build Image") {
            steps {
                echo "Building the latest image..."
                buildImage("${env.BUILD_IMAGE_NAME}")
            }
        }

        // stage("Push Image") {
        // steps {
        //     script {
        //     try {
        //         timeout(time: 60, unit: 'SECONDS') {
        //         isBuild = input message: "Do you want to push latest build? If you gave 'No' to build, please give 'No' here as well",
        //                     parameters: [[$class: 'ChoiceParameterDefinition', name: 'Do you want to Push the new image?', choices: ['Yes', 'No'], description: 'Select yes if you would like to make new build']]
        //         }
        //     }
        //     catch (err) {
        //         echo "${err}"
        //         echo "isBuild passed as Yes"
        //         isBuild = "Yes"
        //     }
        //     //This block tags the built image and pushes it to the ECR_REPO
        //         sh "aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin \"${env.REPO_LOGIN}\""
        //         sh "docker tag ${env.ImageName}:${env.TagId} \"${env.REPO_URL}\":${env.TagId}"
        //         sh "docker push \"${env.REPO_URL}\":${env.TagId}"    
        //     }
        // }
        // }
        // stage("Deploy Latest Build") {
        // steps {
        //     script {
        //     try {
        //         timeout(time: 60, unit: 'SECONDS') {
        //         isDeploy = input message: 'DeployLatestBuild',
        //                     parameters: [[$class: 'ChoiceParameterDefinition', name: 'Would you like to deploy the latest build right away?', choices: ['Yes', 'No'], description: 'Select yes if you would like to deploy the latest build']]
        //         }
        //     }
        //     catch(err) {
        //         echo "${err}"
        //         echo "isDeploy passed as Yes"
        //         isDeploy = "Yes"
        //     }
        //     if (isDeploy == "Yes") {

        //         sh "kubectl apply -f deploy.yaml"
        //         sh "kubectl set image deployment/\"${env.DEPLOYMENT_NAME}\" \"${env.REPO_URL}\":${env.TagId}"

        //     }
        //     }
        // }
        // }
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




// --- multi clone working


// @Library("deployScript") _

// pipeline{
//     agent any 

//     environment {
//     doError = '0'
//     CICD_GIT_REPO = "https://github.com/aamizsameen/test.git"
//     CICD_GIT_BRANCH = "main"
//     CICD_CREDENTIALS_ID = "git"
//     APP_GIT_REPO = "https://github.com/aamizsameen/AWS.git"
//     APP_GIT_BRANCH = "main"
//     APP_CREDENTIALS_ID = "git"
//     BUILD_IMAGE_NAME = "axcess-fe-v1"
//     REPO_URL = "973903430757.dkr.ecr.ap-south-1.amazonaws.com/aamiz-repo"
//     REPO_LOGIN = "973903430757.dkr.ecr.ap-south-1.amazonaws.com"
//     DEPLOYMENT_NAME = "node_app"
//     }


//     stages {

//         stage('CICD Repo') {
//             steps {
//                 echo "Cloning CICD Repo..."
//                 git url: 'https://github.com/aamizsameen/AWS.git', branch: 'main'
//                 echo "CICD Repo Cloned."
//                 dir('..') {

//                     echo "Cloning App Repo..."
//                     git url: 'https://github.com/aamizsameen/test.git', branch: 'main'
//                     echo "App Repo Cloned."   
//                 }
//             }
//         }

//         // stage('Application Repo') {
//         //     steps {
         
//         //     }
//         // }

//         stage("Build Image") {
//             steps {
//                 echo "Building the latest image..."
//                 buildImage("${env.BUILD_IMAGE_NAME}")
//             }
//         }

//         // stage("Push Image") {
//         // steps {
//         //     script {
//         //     try {
//         //         timeout(time: 60, unit: 'SECONDS') {
//         //         isBuild = input message: "Do you want to push latest build? If you gave 'No' to build, please give 'No' here as well",
//         //                     parameters: [[$class: 'ChoiceParameterDefinition', name: 'Do you want to Push the new image?', choices: ['Yes', 'No'], description: 'Select yes if you would like to make new build']]
//         //         }
//         //     }
//         //     catch (err) {
//         //         echo "${err}"
//         //         echo "isBuild passed as Yes"
//         //         isBuild = "Yes"
//         //     }
//         //     //This block tags the built image and pushes it to the ECR_REPO
//         //         sh "aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin \"${env.REPO_LOGIN}\""
//         //         sh "docker tag ${env.ImageName}:${env.TagId} \"${env.REPO_URL}\":${env.TagId}"
//         //         sh "docker push \"${env.REPO_URL}\":${env.TagId}"    
//         //     }
//         // }
//         // }
//         // stage("Deploy Latest Build") {
//         // steps {
//         //     script {
//         //     try {
//         //         timeout(time: 60, unit: 'SECONDS') {
//         //         isDeploy = input message: 'DeployLatestBuild',
//         //                     parameters: [[$class: 'ChoiceParameterDefinition', name: 'Would you like to deploy the latest build right away?', choices: ['Yes', 'No'], description: 'Select yes if you would like to deploy the latest build']]
//         //         }
//         //     }
//         //     catch(err) {
//         //         echo "${err}"
//         //         echo "isDeploy passed as Yes"
//         //         isDeploy = "Yes"
//         //     }
//         //     if (isDeploy == "Yes") {

//         //         sh "kubectl apply -f deploy.yaml"
//         //         sh "kubectl set image deployment/\"${env.DEPLOYMENT_NAME}\" \"${env.REPO_URL}\":${env.TagId}"

//         //     }
//         //     }
//         // }
//         // }
//         // stage("Deploy Different Build") {
//         // steps {
//         //     script {
//         //     try {
//         //         timeout(time: 60, unit: 'SECONDS') {
//         //         isUseOldTaskDefinition = input message: 'Do you want to deploy a different task definition?',
//         //                     parameters: [[$class: 'ChoiceParameterDefinition', name: 'Do you want to deploy a different task definition?', choices: ['Yes', 'No'], description: 'Select yes if you would like to deploy a different task definition?']]
//         //         }
//         //     }
//         //     catch(err) {
//         //         echo "${err}"
//         //         echo "isUseOldTaskDefinition passed as No"
//         //         isUseOldTaskDefinition = "No"
//         //     }
//         //     if (isUseOldTaskDefinition == "Yes") {
//         //         try {
//         //         timeout(time: 60, unit: 'SECONDS') {
//         //             taskNumber = input message: 'Provide the task revision number',
//         //                         parameters: [string(name: 'TaskDefinition Number', description: "Please enter the build number")]
//         //             oldTaskDefinition = "${env.FAMILY}:${taskNumber}"
//         //             echo "$oldTaskDefinition"
//         //         }
//         //         }
//         //         catch(err) {
//         //         echo "${err}"
//         //         echo "TaskNumber cannot be empty and there is no default task number set, requires an user input"
//         //         }
//         //         try {
//         //         timeout(time: 60, unit: 'SECONDS') {
//         //             desiredCount = input message: "DesiredNumberOfConcurrentServices",
//         //                     parameters: [[$class: 'ChoiceParameterDefinition', choices: ["1", "2", "3", "4"],
//         //                             description: 'Please provide the desired number of services you would like to run',
//         //                             name: 'DesiredCount'
//         //                             ]]
//         //         }
//         //         deployImage("${env.CLUSTER}","${env.SERVICE}", "${env.REGION}", "$oldTaskDefinition", "$desiredCount")
//         //         }
//         //         catch(err) {
//         //         echo "${err}"
//         //         echo "No. of services passed as 1"
//         //         desiredCount = "1"
//         //         deployImage("${env.CLUSTER}","${env.SERVICE}", "${env.REGION}", "$oldTaskDefinition", "$desiredCount")
//         //         }  
//         //     }
//         //     }
//         // }
//         // }
//     }
// }


