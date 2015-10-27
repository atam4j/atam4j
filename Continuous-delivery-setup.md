## GNUPG setup for maven artifact signing

* Install gnupg

        $ sudo apt-get install gnupg
        
* Generate key pair

        $ gpg2 --gen-key
    
* Copy secret and public key ring to local repo (Not remote. Remember Do NOT push unencrupted keyrings to remote repo)

* Encrypt public and secret keyrings as follows
    
        $ travis encrypt ENCRYPTION_PASSWORD=****** --add
        $ openssl aes-256-cbc -in pubring.gpg -out pubring.gpg.enc -pass pass:******
        $ openssl aes-256-cbc -in ~/Desktop/secring.gpg -out secring.gpg.enc -pass pass:******    

* Setup .travis.yml
    
        after_success:
          - echo "<settings><servers><server><id>ossrh</id><username>\${env.OSSRH_USER}</username><password>\${env.OSSRH_PASS}</password></server></servers></settings>" > ~/settings.xml
          - mvn deploy -Dgpg.defaultKeyring=false -Dgpg-keyname=BF230DCD -Dgpg.passphrase=${env.OSSRH_PASS} -Dgpg.publicKeyring=pubring.gpg -Dgpg.secretKeyring=secring.gpg --settings ~/settings.xml

## Maven release - commit back to github from travis

* Generate new key pair

        ssh-keygen -t rsa -b 4096 -C "anurag+atam4j@beancrunch.com"

* Convert private key to .pem format
    
        $ openssl rsa -in ~/.ssh/atam4j_id_rsa -outform pem > atam4j_id_rsa.pem
    
* Encrypt .pem key
    
        $ openssl aes-256-cbc -in atam4j_id_rsa.pem -out atam4j_id_rsa.pem.enc -pass pass:******
    
* Commit encrypted key to git

* Add decryption command to .travis.yml
    
        - openssl aes-256-cbc -pass pass:$PEM_ENCRYPTION_PASSWORD -in keys/atam4j_id_rsa.pem.enc -out ~/atam4j_id_rsa.pem -d
    
* Update travis config to use pem for git push
    
        after_success:
        - eval "$(ssh-agent -s)" #start the ssh agent
        - chmod 600 ~/atam4j_id_rsa.pem # this key should have push access
        - ssh-add ~/atam4j_id_rsa.pem
        - git remote add origin git@github.com:atam4j/atam4j.git

* Ignore maven release plugin commits from travis - automatic builds

        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-release-plugin</artifactId>
            <version>2.5.3</version>
            <configuration>
                <scmCommentPrefix>[ci skip]</scmCommentPrefix>
            </configuration>
        </plugin>
        
## Useful Commands
    
    # List keys     
    $ gpg --list-secret-keys
    
## References
* http://notbarjo.blogspot.co.uk/2014/09/travis-ci-maven-deploy.html
* http://benlimmer.com/2014/01/04/automatically-publish-to-sonatype-with-gradle-and-travis-ci/
* https://maven.apache.org/plugins/maven-gpg-plugin/sign-mojo.html
* https://www.theguardian.com/info/developer-blog/2014/sep/16/shipping-from-github-to-maven-central-and-s3-using-travis-ci
* http://stackoverflow.com/questions/18027115/committing-via-travis-ci-failing

