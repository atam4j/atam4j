atam4j
======

Acceptance Tests As Monitors

## Latest Build Status

[![Build Status](https://travis-ci.org/atam4j/atam4j.svg?branch=master)](https://travis-ci.org/atam4j/atam4j)

## Using atam4j

### Maven Dependency
    <dependency>    
       <groupId>me.atam</groupId>    
       <artifactId>atam4j</artifactId>    
       <version>0.1.0</version>    
    </dependency>


### Available Versions
#### Snapshots
[https://oss.sonatype.org/content/repositories/snapshots/me/atam/atam4j/](https://oss.sonatype.org/content/repositories/snapshots/me/atam/atam4j/)

#### Releases
Look for non-snapshot version
[https://oss.sonatype.org/content/groups/public/me/atam/atam4j/](https://oss.sonatype.org/content/groups/public/me/atam/atam4j/)

## Developing atam4j
    
### Build and Release

#### Building Locally
> mvn clean install

#### Running Tests using Maven CLI
> mvn clean test

#### Release - Manual
##### Prerequisite
Only [core committers](Core-Committers.md) can release atam4j to maven central. You need Sonatype Nexus OSS account info for atam.me.

##### Steps
> mvn versions:set -DnewVersion={version number}    
mvn clean deploy






