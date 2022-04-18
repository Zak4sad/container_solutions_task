#!/bin/bash
cd helm/app
ls
helm dependency update
helm install -f values.yaml app .