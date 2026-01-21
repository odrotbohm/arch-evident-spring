#!/bin/bash
counter=0

# Remove all step tags
git tag --list 'steps/*' | xargs -r git tag -d

# Iterate all commits in reverse order
for branch in $(git log --format="%h" --reverse);
do
    git tag "steps/$counter" $branch
    ((counter++))
done
