!#bin/bash

kubectl apply -f 0_userfordashboard.yaml

kubectl -n kubernetes-dashboard create token admin-user

kubectl -n kubernetes-dashboard port-forward svc/kubernetes-dashboard-kong-proxy 8443:443
