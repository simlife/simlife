#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 \"<version>\"" >&2
    exit 1
fi

SIMLIFE_VERSION=$1 travis/scripts/00-replace-version-simlife.sh
