terraform {
  required_providers {
    mycloud = {
      source  = "hashicorp/kubernetes"
      version = "~> 1.13"
    }
  }
  backend "local" {
      path = "/tmp/terraform.tfstate"
  }
}

provider "kubernetes" {
  # the result of this value can be obtained by running this command :  
  #            kubectl config view|grep server
  host = "https://kubernetes.docker.internal:6443" # I used here my local Docker Desktop cluster 
}