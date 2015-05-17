atam4j
======

[![Join the chat at https://gitter.im/atam4j/atam4j](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/atam4j/atam4j?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Acceptance Tests As Monitors

* A simple library that allows you to run junit tests as monitoring checks.
* Runs tests on a schedule and exposes results via a restful api that can called from your monitoring (e.g. nagios, zabbix, icinga, pingdom etc etc)

## Latest Build Status

[![Build Status](https://travis-ci.org/atam4j/atam4j.svg?branch=master)](https://travis-ci.org/atam4j/atam4j)

## Maven Dependency
    <dependency>    
       <groupId>me.atam</groupId>    
       <artifactId>atam4j</artifactId>    
       <version>0.3.0</version>    
    </dependency>

## Using atam4j

1. Include the atam4j maven dependency.

2. Write Junit based tests in the usual manner, with the exception of adding a `@Monitor` annotation to the class and including them in the main application classpath 
instead of the tests classpath. This can be done by including them in the `src/main/java directory` instead of the 
`src/test/java` directory.

3. Instantiate  `Atam4j` in the `run` method of your dropwizard application class.    

        new Atam4j.Atam4jBuilder()     
            .withTestClasses(new Class[]{HelloWorldTest.class})     
            .withEnvironment(environment)      
            .build()      
            .initialise();

4. Run the dropwizard app and observe the status of the acceptance tests reported under the health-check endpoint.

Refer to [atam4j-sample-app](https://github.com/atam4j/atam4j-sample-app) for a complete working example.

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

1. Set version of the release    
> mvn versions:set -DnewVersion=${versionNumber}

2. Commit the version back to git and push to remote 
> git commit -a -m "Preparing release v${versionNumber}"    
git push

3. Tag code 
> git tag -a v${versionNumber} -m 'version ${versionNumber}’
git push origin v${versionNumber}
    
4. Create release in Github - [https://github.com/atam4j/atam4j/releases](https://github.com/atam4j/atam4j/releases)    

5. Deploy to maven central    
> mvn clean deploy







