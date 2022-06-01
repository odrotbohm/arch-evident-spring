#!/bin/bash
# Push current state
git push origin main --force-with-lease

# Renew tags
source "${BASH_SOURCE%/*}/retag.sh"
source "${BASH_SOURCE%/*}/remove-remote-tags.sh"
git push origin --tags
