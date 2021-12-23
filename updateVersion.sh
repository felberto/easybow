#!/bin/bash
echo Updating Version to $1
mvn versions:set -DnewVersion=$1
npm version $1
npm publish
