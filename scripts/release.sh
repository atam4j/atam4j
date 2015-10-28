#!/bin/bash

if [[ $TRAVIS_BRANCH == 'master' && $TRAVIS_PULL_REQUEST = "false" ]]; then
    git checkout master
    git remote add origin git@github.com:atam4j/atam4j.git
    mvn release:prepare release:perform -Darguments="-Dgpg.defaultKeyring=false -Dgpg-keyname=BF230DCD -Dgpg.passphrase=$GPGKEY_PASSPHRASE -Dgpg.publicKeyring=~/pubring.gpg -Dgpg.secretKeyring=~/secring.gpg -DskipTests=true -DskipTests=true" -B -Prelease --settings ~/settings.xml
fi

