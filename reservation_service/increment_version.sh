#!/bin/bash

# Extract the branch name
branch_name=$(git rev-parse --abbrev-ref HEAD)

version=$(cat version.txt | tr -d '[:space:]')

if [[ ! -f version.txt ]] || [[ -z "$version" ]]; then
    echo "Error: version.txt does not exist or is empty."
    exit 1
fi

IFS='.' read -ra versionParts <<< "$version"

major=${versionParts[0]}
minor=${versionParts[1]}
patch=${versionParts[2]}

case $branch_name in
    /^\/version.*$/)
        ((major++))
        ;;
    /^\/feature.*$/)
        ((minor++))
        ;;
    /^\/refactor.*$/|^/^\/bug-fix.*$/)
        ((patch++))
        ;;
esac

newVersion="${major}.${minor}.${patch}"

echo "$newVersion" > version.txt

echo "$newVersion"