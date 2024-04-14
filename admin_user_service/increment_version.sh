#!/bin/bash

versionFile=$(<version.txt)
version=$(echo "$versionFile" | tr -d '[:space:]')

IFS='.' read -ra versionParts <<< "$version"

major=${versionParts[0]}
minor=${versionParts[1]}
patch=${versionParts[2]}

if (( patch < 10 )); then
    ((patch++))
elif (( minor < 10 )); then
    ((minor++))
    patch=0
else
    ((major++))
    minor=0
    patch=0
fi

newVersion="${major}.${minor}.${patch}"

echo "Incremented Version: ${newVersion}"

echo "$newVersion" > version.txt
