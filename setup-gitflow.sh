#!/bin/bash

if [ ! -d ".git" ]; then
  echo "Inicializando repositorio Git..."
  git init
fi

echo "Inicializando Git Flow con configuración por defecto..."
git flow init -d

git checkout -b develop || echo "Rama 'develop' ya existe."
git checkout main

echo "✅ Git Flow inicializado correctamente."
echo ""
echo "Comandos rápidos que puedes usar:"
echo " - git flow feature start nombre        # Nueva funcionalidad"
echo " - git flow release start 1.0.0         # Nueva versión"
echo " - git flow hotfix start arreglo-critico # Corrección urgente"
