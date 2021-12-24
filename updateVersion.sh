#!/bin/bash
echo Updating Version to $1
mvn versions:set -DnewVersion=$1
git add -A
git commit -m "mvn version to $1"
npm version $1
npm publish
