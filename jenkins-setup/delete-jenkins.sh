kubectl delete -f jenkins-service.yml
kubectl delete -f jenkins-deployment.yml
kubectl delete -f jenkins-persistent-volume-claim.yml
kubectl delete -f jenkins-persistent-volume.yml
kubectl delete namespace jenkins
rm -rf /mnt/jenkins/*