#!/bin/bash

version=$(cat version.txt)

if [[ ! -f version.txt ]] || [[ -z "$version" ]]; then
    echo "Error: version.txt does not exist or is empty."
    exit 1
fi

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

echo "$newVersion"

echo "$newVersion" > version.txt