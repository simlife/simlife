#!/bin/bash

if [[ $SIMLIFE_VERSION == '' ]]; then
    SIMLIFE_VERSION=0.0.0-TRAVIS
fi

# artifact version of simlife-parent
sed -e '/<artifactId>simlife-parent<\/artifactId>/{N;s/<version>.*<\/version>/<version>'$SIMLIFE_VERSION'<\/version>/1;}' pom.xml > pom.xml.sed
mv -f pom.xml.sed pom.xml

# simlife-framework.version property in simlife-parent
sed -e 's/<simlife-framework.version>.*<\/simlife-framework.version>/<simlife-framework.version>'$SIMLIFE_VERSION'<\/simlife-framework.version>/1' pom.xml > pom.xml.sed
mv -f pom.xml.sed pom.xml

# parent version of simlife-dependencies
sed -e '/<artifactId>simlife-parent<\/artifactId>/{N;s/<version>.*<\/version>/<version>'$SIMLIFE_VERSION'<\/version>/1;}' simlife-dependencies/pom.xml > simlife-dependencies/pom.xml.sed
mv -f simlife-dependencies/pom.xml.sed simlife-dependencies/pom.xml

# parent version of simlife-framework
sed -e '/<artifactId>simlife-dependencies<\/artifactId>/{N;s/<version>.*<\/version>/<version>'$SIMLIFE_VERSION'<\/version>/1;}' simlife-framework/pom.xml > simlife-framework/pom.xml.sed
mv -f simlife-framework/pom.xml.sed simlife-framework/pom.xml

