#!/bin/bash

# Check out initial step
git reset --hard
git checkout steps/0

# Open documentation in Browser
open -n -a "Brave Browser" --args "--new-window" `pwd`/src/main/asciidoc/index.adoc
