job('App Deployment via Automated Jenkin Job')
{

//For setting description of the job
description('This is the automated job created by seed job. This task performs tests on the code pulled from GitHub repository and builds the whole code. Afterwards, with the help of Docker & Ansible build is deployed to production and staging servers over SSH via Key Authentication.')

//Used for discarding old builds
logRotator 
{
    numToKeep(1)
    daysToKeep(1)
}

//confiuring job with build parameters
parameters
{
        choiceParam('Servers', ['Staging', 'Production'], 'Staging - The test server for testing patches, upgrades etc.\nProduction - The real time server for application')
}

//integrating with GitHub repository with main branch
scm {
        github('emad-hussain/CI-CD-Project', 'master')
    }

//allowing Job to check source code every minute and auto run after each commit in GitHub repository
    triggers {
        scm('* * * * *')
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
            server('production') {
                credentials('eetch') {
                    pathToKey('id_rsa') 
                }
                transferSet {
                    sourceFiles('webapp/target/webapp.war')
                    removePrefix('webapp/target/')
                    remoteDirectory('.')
                }
                
                transferSet {
                    sourceFiles('CI-CD-Pipeline/Dockerfile')
                    remoteDirectory('.')
                    
                }
            }

        }
}

}
