#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

cd "${ROOT_DIR}/services/workspace-service"
./mvnw clean package -DskipTests

cd "${ROOT_DIR}"

docker compose \
  --env-file infra/docker-compose/.env.example \
  -f infra/docker-compose/docker-compose.yml \
  up -d --build