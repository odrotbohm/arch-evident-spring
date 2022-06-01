#!/bin/bash
# Iterate all commits in reverse order
git checkout main

for commit in $(git log --reverse --format=format:%h);
do
    git checkout $commit && ./mvnw -B -U clean verify
    ((counter++))
done

git checkout main
