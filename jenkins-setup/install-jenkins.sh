kubectl create namespace jenkins
kubectl apply -f jenkins-persistent-volume.yml
kubectl apply -f jenkins-persistent-volume-claim.yml
kubectl create sa jenkins --namespace jenkins
kubectl create rolebinding jenkins-admin-binding --clusterrole=admin --serviceaccount=jenkins:jenkins --namespace=jenkins
kubectl apply -f jenkins-deployment.yml
kubectl apply -f jenkins-service.yml