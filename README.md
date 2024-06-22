
# There are few components in SmallBank as below
    1) Rabitmq
    2) Configuration Server
    3) Eureka Server
    4) Application Gateway
    5) Keycloak
    6) users  
    7) cards
    8) Prometheus
    9) Loki
    10) Tempo
    11) Grafana

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

# Running the images on docker by executing command for each docker image:
    Once all the images are prepared, we can execute commands like below to strt the container.

        1) go to each helm chart folder and execute below command (Helm chart directories : kafka, rabbitmq, keycloak, kube-prometheus, grafana-loki,
            grafana-tempo, grafana, environments/dev-env, smallbank-services/configurations, smallbank-services/eureka-servicediscovery,
            smallbank-services/gatewayserver, smallbank-services/users, smallbank-services/cards, smallbank-services/messages )
            
            helm dependencies build

        2) From helm directory execute below commands one after another to git the required modules up in cluster
            helm install kafka kafka                        # Starts Kafka
            helm install rabbitmq rabbitmq                  # Starts rabbitmq
            helm install install keycloak keycloak          # Starts keycloak
            helm install prometheus kube-prometheus         # Starts Prometheus
            helm install loki grafana-loki                  # Starts Loki
            helm install tempo grafana-tempo                # Starts Tempo
            helm install grafana grafana                    # Starts grafana

        3) Navigate to environments directory
            
            helm install smallbank dev-env                  # Starts All Smallbank services in single command
    
    Commands ran in step 1 will build all the modules after downloading all the required dependencies.
    Commands in step 2 will start all the supporting applications
    Commands in step 3 willl start all the smallbank applications


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
    


