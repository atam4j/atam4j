#!/bin/bash

if [[ $TRAVIS_PULL_REQUEST == 'false' && $TRAVIS_BRANCH == 'master' ]]; then
    git remote add origin git@github.com:atam4j/atam4j.git
    mvn release:prepare release:perform -DskipTests=true -B
    mvn deploy -Prelease -Dgpg.defaultKeyring=false -Dgpg-keyname=BF230DCD -Dgpg.passphrase=$GPGKEY_PASSPHRASE -Dgpg.publicKeyring=~/pubring.gpg -Dgpg.secretKeyring=~/secring.gpg -DskipTests=true --settings ~/settings.xml
fi

