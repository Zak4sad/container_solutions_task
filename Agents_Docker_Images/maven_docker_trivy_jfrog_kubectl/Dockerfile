FROM ubuntu:latest
# Install maven
RUN apt-get update \
    && apt-get -y install maven
# Install docker
RUN apt install -y docker.io
# Install Trivy
RUN apt-get install -y wget curl apt-transport-https gnupg lsb-release \
    && wget -qO - https://aquasecurity.github.io/trivy-repo/deb/public.key | apt-key add - \
    && echo deb https://aquasecurity.github.io/trivy-repo/deb $(lsb_release -sc) main | tee -a /etc/apt/sources.list.d/trivy.list \
    && apt-get update \
    && apt-get install -y trivy
# Install JFRO CLI
RUN wget -qO - https://releases.jfrog.io/artifactory/jfrog-gpg-public/jfrog_public_gpg.key | apt-key add - \
    && echo "deb https://releases.jfrog.io/artifactory/jfrog-debs xenial contrib" | tee -a /etc/apt/sources.list \
    && apt update \
    && apt install -y jfrog-cli-v2-jf && jf intro
# Install Kubectl
RUN curl -LO "https://storage.googleapis.com/kubernetes-release/release/v1.20.5/bin/linux/amd64/kubectl" 
RUN chmod u+x ./kubectl
RUN mv ./kubectl /usr/local/bin/kubectl