job('App Deployment via Automated Jenkin Job')
{

//For setting description of the job
description('This is the automated job created by seed job. This task performs tests on the code pulled from GitHub repository and builds the whole code. Afterwards, with the help of Docker & Ansible code is deployed to production and staging servers.')

//Used for discarding old builds
logRotator 
{
    numToKeep(1)
    daysToKeep(1)
}

//confiuring job with build parameters
parameters
{
        choiceParam('Servers', ['Production', 'Staging'], 'Production - The real time server for application \nStaging - The test server for testing patches, upgrades etc.')
}

//integrating with GitHub repository with main branch
scm {
        github('emad-hussain/CI-CD-Project', 'master')
    }

//allowing Job to check source code every minute and auto run after each commit in GitHub repository
    triggers {
        scm('* * * * *') {
            ignorePostCommitHooks()
        }
    }

//delete workspace before build start
wrappers {
        preBuildCleanup()
    }

//integrating maven with Jenkins at buildSteps
steps
{
    maven
    {
        goals('test')
        goals('install')
        mavenInstallation('localmaven')
    }
    
}

//configuring postBuildSteps with publishOverSsh plugin
steps {
        publishOverSsh {
            server('master') {
                credentials('eetch') {
                    encryptedPassphrase : 11
                }
                transferSet {
                    sourceFiles('webapp/target/webapp.war')
                    removePrefix('webapp/target/')
                    remoteDirectory('.')
                    execCommand('ansible-playbook step1.yml --limit ${Servers}\nansible-playbook step2.yml --limit ${Servers}')
                }
            }

        }
}

}
