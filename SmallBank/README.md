
# There are 3 components as below
    1) configuration Server
    2) users 
    3) cards

    users, cards are the services which connect to configuration servers to find out any configuration changes.

# All the three services use Google jib to build docker image as below:
    1) In pom.xml add the google jib plugin.
    2) Add managed dependency and cloud version variable
    3) Set iamge name in the plugin.  (Example image name : kiranfegade19/configurations:1.0.0)
    4) Set packaging as jar in pom.xml
    5) Use below command to create docker image using google gib from application home directories:
        mvn compile jib:dockerImage

# Running the images on docker:
    Once all the images are prepared, we can execute commands like below to strt the container.

    
        docker run -d --name configurations-ms -p 8000:8000 kiranfegade19/configurations:1.0.0
        docker run -d --name users-ms -p 8030:8010 kiranfegade19/users:1.0.0
        docker run -d --name cards-ms -p 8040:8020 kiranfegade19/cards:1.0.0
    
    These commands will start the configurations, users and cards containers.
    
# Accessing Swagger endpoints
    On your local system open browser and hit below URL's to access Swagger pages.

    users-ms swagger : http://localhost:8030/swagger-ui/index.html
    cards-ms swagger : http://localhost:8040/swagger-ui/index.html

