pipeline{
    agent {
        node {
            label 'master'
            customWorkspace "/home/ahmed/hello"
        }
    }
    stages{
		stage("build"){
			steps {
			    withMaven(options:[artifactsPublisher(disabled:true)], maven : 'apache-maven-3.6.3') {

                    sh "mvn clean install -DskipTests"
                }

	    	}
        }
	}
}