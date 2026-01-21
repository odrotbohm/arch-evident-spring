#!/bin/bash
# Iterate all commits in reverse order
for branch in $(git tag --list 'steps/*');
do
    git checkout $branch && ./mvnw -B -U clean verify
    ((counter++))
done

git checkout main
