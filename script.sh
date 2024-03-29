#!/bin/bash


TAG_ID=$(kubectl get deployment ${DEPLOYMENT_NAME} -n ${NAMESPACE} -o=jsonpath='{.spec.template.spec.containers[0].image}' | awk -F':' '{printf "%.1f", $2 + 0.1}')

echo $TAG_ID

export $TAG_ID
