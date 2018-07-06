#!/bin/bash

if [[ $SIMLIFE_VERSION == '' ]]; then
    SIMLIFE_VERSION=0.0.0-TRAVIS
fi

# simlife-dependencies.version in generated pom.xml or gradle.properties
PREVIOUS_DIR=$(pwd)
cd $HOME/generator-simlife/generators/server/templates

sed -e 's/<simlife-dependencies.version>.*<\/simlife-dependencies.version>/<simlife-dependencies.version>'$SIMLIFE_VERSION'<\/simlife-dependencies.version>/1;' pom.xml.ejs > pom.xml.ejs.sed
mv -f pom.xml.ejs.sed pom.xml.ejs
cat pom.xml.ejs | grep \<simlife-dependencies.version\>

sed -e 's/simlife_dependencies_version=.*/simlife_dependencies_version='$SIMLIFE_VERSION'/1;' gradle.properties.ejs > gradle.properties.ejs.sed
mv -f gradle.properties.ejs.sed gradle.properties.ejs
cat gradle.properties.ejs | grep simlife_dependencies_version=

cd "$PREVIOUS_DIR"
