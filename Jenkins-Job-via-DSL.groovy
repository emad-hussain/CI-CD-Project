job('Project Deployment Automated Pipeline')
{
    description('This is the Auto Jenkins job created for Java Application testing and deployment using DSL Script pulled from GitHub via Seed Job. This task performs tests on the code pulled from GitHub repository and builds the whole code. Afterwards, with the help of Docker & Ansible code is deployed to production and staging servers.')

    scm {
        github('https://github.com/emad-hussain/CI-CD-Project.git', 'main')
    }
    
    discardOldBuilds(int daysToKeep = 1, int numToKeep = 1)

}
