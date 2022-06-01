#!/bin/bash

# Iterate all commits in reverse order
for branch in $(git tag --list 'steps/*');
do
    git push origin ":"$branch
done
