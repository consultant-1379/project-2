This is a README for Project 2.

Useful commands to be run from terminal:
* 'mvn clean' before pushing (this will stop target directory from being pushed to Gerrit)

* Starting a SonarQube server locally:
    - docker pull sonarqube
    - docker run -d --name sonarqube -p 9000:9000 sonarqube
    - Navigate to localhost:9000 and log in with your browser (username and password are both admin), once inside:
        - Give it whatever project key / name you want, I just use 'test'
        - Create a new project using the Java / maven options.
        - It will give you a command to be run from within the project, if you're getting errors when running it then you may need to remove the backslashes.
    - Go to localhost:9000 when the command has finished running, you should see a detailed report.
    
* Solving issues regarding dependencies not being found:
    - IntelliJ removed automatic dependency imports, so instead you must use CTRL+SHIFT+O
    
* Using Metrics-Jenkins-API
    - Ensure you have started the metrics-jenkins-api microservice.
    - To see the jobs which are set up to run builds, use a: 
        - GET request: http://localhost:8080/jobs
    - If you wish to see the builds associated with the job, use a: 
        - GET request with the job ID: http://localhost:8080/jobs/eric-son-frequency-layer-manager_PreCodeReview