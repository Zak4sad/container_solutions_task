resource "kubernetes_persistent_volume" "local-volume" {
  metadata {
    name = "local-volume"
    labels = {
      type = "local"
    }
  }
  spec {

    persistent_volume_source {
      host_path {
        path = "/mnt/data"
      }
    }

    storage_class_name = "hostpath"
    capacity = {
      storage = "2Gi"
    }
    access_modes = ["ReadWriteOnce"]
  }
}

resource "kubernetes_persistent_volume_claim" "local-claim" {
  metadata {
    name = "local-claim"
  }
  spec {
    storage_class_name = "hostpath"
    access_modes = ["ReadWriteOnce"]
    resources {
      requests = {
        storage = "1.5Gi"
      }
    }
  }
}

resource "kubernetes_secret" "container-solution-app-secret" {
  metadata {
    name = "container-solution-app-secret"
  }
  data = {
    mysqldb-user = "root"
    mysqldb-root-password = "MTIzNDU2"
    mysqldb-database = "YmV6a29kZXJfZGI="
  }
  type = "Opaque"
}

resource "kubernetes_secret" "mysql-db-secret" {
  metadata {
    name = "mysql-db-secret"
  }
  data = {
    mysqldb-root-password = "MTIzNDU2"
    mysqldb-database = "YmV6a29kZXJfZGI="
  }
  type = "Opaque"
}

resource "kubernetes_deployment" "container-solution-app" {
  metadata {
    name = "container-solution-app"
    labels = {
      App = "container-solution-app"
    }
  }

  spec {
    replicas = 1
    selector {
      match_labels = {
        App = "container-solution-app"
      }
    }
    template {
      metadata {
        labels = {
          App = "container-solution-app"
        }
      }
      spec {
        container {
          image = "container-solution-app:latest"
          name  = "container-solution-app"
          image_pull_policy = "IfNotPresent"

          port {
            container_port = 8080
          }

          env {
            name = "MYSQLDB_DATABASE"
        value_from {
              secret_key_ref {
                name = "${kubernetes_secret.container-solution-app-secret.metadata.0.name}"
                key = "mysqldb-database"
              }
            }
          }

          env {
            name  = "MYSQLDB_USER"
            value_from {
              secret_key_ref {
                key  = "mysqldb-user"
                name = "${kubernetes_secret.container-solution-app-secret.metadata.0.name}"
              }
            }
          }
          env {
            name  = "MYSQLDB_ROOT_PASSWORD"
            value_from {
              secret_key_ref {
                key  = "mysqldb-root-password"
                name = "${kubernetes_secret.container-solution-app-secret.metadata.0.name}"
              }
            }
          }
          env {
            name  = "MYSQLDB_SERVICE"
            value = "mysql-service"
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "container-solution-app-service" {
  metadata {
    name = "container-solution-app-service"
  }
  spec {
    selector = {
      App = "${kubernetes_deployment.container-solution-app.metadata.0.labels.App}"
    }
    port {
      port        = 8080
      target_port = 8080
    }
    type = "LoadBalancer"
}
}

resource "kubernetes_stateful_set" "mysql" {
  metadata {
    name = "mysql"
    labels = {
      App = "mysql"
    }
  }

  spec {
    selector {
      match_labels = {
        "App" = "mysql"
      }
    }
    service_name = "mysql"
    template {
      metadata {
        labels = {
          App = "mysql"
        }
      }
      spec {
        container {
          image = "mysql:5.7"
          name  = "mysql"
          image_pull_policy  = "IfNotPresent"

          port {
            container_port = 3306
          }

          env {
            name  = "MYSQL_ROOT_PASSWORD"
            value_from {
              secret_key_ref {
                key  = "mysqldb-root-password"
                name = "${kubernetes_secret.mysql-db-secret.metadata.0.name}"
              }
            }
          }

          env {
            name  = "MYSQL_DATABASE"
            value_from {
              secret_key_ref {
                key  = "mysqldb-database"
                name = "${kubernetes_secret.mysql-db-secret.metadata.0.name}"
              }
            }
          }

          volume_mount {
            mount_path = "/var/lib/mysql"
            name = "data" 
          }

        }

        volume {
          name = "data"
          persistent_volume_claim {
            claim_name = "local-claim"
          }
        }

        image_pull_secrets {
          name = "mysql-db-secret"
        }
      }

    }
  }
}

resource "kubernetes_service" "mysql-service" {
  metadata {
    name = "mysql-service"
  }
  spec {
    selector = {
      App = "${kubernetes_stateful_set.mysql.metadata.0.labels.App}"
    }
    port {
      port        = 3306
      target_port = 3306
    }
    type = "ClusterIP"
  }
}

resource "kubernetes_ingress" "bezkoder-rest-api" {
  metadata {
    name = "bezkoder-rest-api"
  }

  spec {
    backend {
      service_name = "container-solution-app-service"
      service_port = 8080
    }

    rule {
      host = "my-rest-api.zakariaa.sadek.com"
      http {
        path {
          backend {
            service_name = "container-solution-app-service"
            service_port = 8080
          }

          path = "/"
        }
      }
    }
  }
}