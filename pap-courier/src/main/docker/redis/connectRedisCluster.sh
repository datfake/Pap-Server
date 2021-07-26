#!/usr/bin/env bash

function log {
  echo "[$(date)]: $*"
}

log "Start Redis Cluster builder"
sleep 5

log "Connect all Redis containers"
redis-cli \
  --cluster-replicas 1 \
  --cluster-yes \
  --cluster create \
    $(host courier_server-redis|awk '{print $4}'):6379 \
    $(host courier_server-redis-1|awk '{print $4}'):6379 \
    $(host courier_server-redis-2|awk '{print $4}'):6379 \
    $(host courier_server-redis-3|awk '{print $4}'):6379 \
    $(host courier_server-redis-4|awk '{print $4}'):6379 \
    $(host courier_server-redis-5|awk '{print $4}'):6379
