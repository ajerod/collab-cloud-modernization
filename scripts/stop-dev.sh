#!/usr/bin/env bash
set -euo pipefail

docker compose \
  --env-file infra/docker-compose/.env.example \
  -f infra/docker-compose/docker-compose.yml \
  down