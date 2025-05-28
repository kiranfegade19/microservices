
# There are few components in SmallBank as below
    HelmChart Projects   (/microservices/SmallBank/helm/side-car-charts/)
        1) Rabitmq
        2) Keycloak
        3) Prometheus
        4) Loki
        5) Tempo
        6) Grafana
        7) Jenkins
        8) Kafka

    Custom Projects      (/microservices/SmallBank/)
        1) Configuration Server
        2) Eureka Server
        3) Application Gateway
        4) users  
        5) cards
    
    users, cards are the services which are notified by configuration server to find out any configuration changes on runtime using rabbitmq.
    Service discovery is handled by Eureka server.
    Keycloak is the authorization server for providing OAuth2 security.
    Gateway server acts as single entrypoint and also acts as resource for OAuth security.

# Configuration properties
    Configuraton properties of components are stored in Small-Bank-Configurations.
    Whenever any changes are pushed to configuration files, those changes are loaded on runtime in 
    users and cards services without any downtime.

# Webhooks usage
    Whenever changes are pushed to repository, webhook is configured in repository to send the event on /monitor API of 
    configurations service exposed with the help of "spring-cloud-config-monitor" dependency.
    
    https://consolehookdeck.com website is used to for generating web hook url, which will call /monitor API of 
    configserver service.

# All the services use Google jib to build docker image as below:
    1) In pom.xml add the google jib plugin.  (jib works with java projects only)
    2) Add managed dependency and cloud version variable
    3) Set iamge name in the plugin.  (Example image name : kiranfegade19/configurations:1.0.0)
    4) Set packaging as jar in pom.xml
    5) Use below command to create docker image using google gib from application home directories:
        mvn compile jib:dockerBuild
        docker push kiranfegade19/cards:1.0.0

# Running the HelmChart components:
    Once all the images are prepared, we can execute commands like below (using Manual or automated steps) to start the container.

### Manual Steps
        1) Go to each path and each directory as mentioned below:
            Path 1: /microservices/SmallBank/helm/side-car-charts/
                rabitmq, keycloak, kube-prometheus, grafana-loki, grafana-tempo, grafana, jenkins, kafka
            Path 2: /microservices/SmallBank/helm/smallbank-services/
                cards, configurations, eureka-servicediscovery, gatewayserver, messages, users
            Path 3: /microservices/SmallBank/helm/smallbank-helmchart/
            Path 4: /microservices/SmallBank/helm/environments/
                dev-env
            
        Execute the below command
            helm dependencies build

        2) From side-car-charts directory (/microservices/SmallBank/helm/side-car-charts/) execute below commands one after another to start the required modules in cluster
            helm install kafka kafka                        # Starts Kafka
            helm install rabbitmq rabbitmq                  # Starts rabbitmq
            helm install install keycloak keycloak          # Starts keycloak
            helm install prometheus kube-prometheus         # Starts Prometheus
            helm install loki grafana-loki                  # Starts Loki
            helm install tempo grafana-tempo                # Starts Tempo
            helm install grafana grafana                    # Starts grafana
            helm install jenkins jenkins                    # Starts jenkins

        3) Navigate to environments directory (/microservices/SmallBank/helm/environments/) and execute below command to start custome applications with dev environment:
            
            helm install smallbank-dev-env dev-env                  # Starts All Smallbank services in single command
    
    Commands ran in step 1 will build all the modules after downloading all the required dependencies.
    Commands in step 2 will start all the supporting applications
    Commands in step 3 willl start all the smallbank applications in dev envoronment.

### Automated Steps
    1) Go to starters directory (/microservices/SmallBank/helm/side-car-charts/) and execute below sh script:
        ./startAll.sh

    2) Go to environments directory (/microservices/SmallBank/helm/environments/) and execute below sh script:
        ./startSmallbank.sh

# Test Live configuration reload
    1) Consume /test API on both the services users and cards and check the values.
    2) Now make some changes in the configurations present in SmallBank-Configurations directory.
    3) Once the changes are pushed, sequesnce of events as below will be triggered:
        a. Webhook url will call the /monitor API to inform configserver regarding changes.
        b. As configserver receives the event on /monitor API, it reloads the configurations from the repository.
        c. configserver sends the refresh events on rabitmq to all config clients (users, cards)
        d. Config clients (users, cards) reads these events and update the configuration on runtime.

    NOTE : 
        1. Using webhooks, live configuration reload is automated.
        2. Same live config could be updated by manually consuming /refresh API of the Config clients (users, cards)
        3. hookdeck free webhook might expire after some threshold. Once expired new webhook should be updated in repository.
        4. Only user who is registered in Keycloak and have USERS and CARDS role can execute the users and cards API.


# URL's
    Prometheus:             http://localhost:9090/
    Grafana:                http://localhost:3000/
    Keycloak:               http://localhost/
        
    Users-test              http://localhost:8002/smallbank/users/api/v1/user/test
    Users-Post              http://localhost:8002/smallbank/users/api/v1/user                               (OAuth2 Authenticated)
    Users-Fetch             http://localhost:8002/smallbank/users/api/v1/user?mobileNumber=0123456789
    Users-CB-Events         http://localhost:8002/users/actuator/circuitbreakerevents
    Users-CB-Status         http://localhost:8002/users/actuator/circuitbreakers
        
    Cards-test              http://localhost:8002/smallbank/cards/api/v1/cards/test
    Cards-Post              http://localhost:8002/smallbank/cards/api/v1/cards                              (OAuth2 Authenticated)
    Cards-Fetch             http://localhost:8002/smallbank/cards/api/v1/cards?mobileNumber=0123456789
    Cards-Delete            http://localhost:8002/smallbank/cards/api/v1/cards?cardId=1
    
    GatewayServer-Routes    http://localhost:8002/actuator/gateway/routes
    


