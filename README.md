# vlingo-telemetry

[![Javadocs](http://javadoc.io/badge/io.vlingo/vlingo-telemetry.svg?color=brightgreen)](http://javadoc.io/doc/io.vlingo/vlingo-telemetry) [![Build Status](https://travis-ci.org/vlingo/vlingo-telemetry.svg?branch=master)](https://travis-ci.org/vlingo/vlingo-telemetry) [ ![Download](https://api.bintray.com/packages/vlingo/vlingo-platform-java/vlingo-telemetry/images/download.svg) ](https://bintray.com/vlingo/vlingo-platform-java/vlingo-telemetry/_latestVersion)

The reactive metrics collector plugin for the vlingo/platform, including support for vlingo/actors, vlingo/http, vlingo/lattice, vlingo/streams, and others.

### Bintray

```xml
  <repositories>
    <repository>
      <id>jcenter</id>
      <url>https://jcenter.bintray.com/</url>
    </repository>
  </repositories>
  <dependencies>
    <dependency>
      <groupId>io.vlingo</groupId>
      <artifactId>vlingo-telemetry</artifactId>
      <version>0.7.7</version>
      <scope>compile</scope>
    </dependency>
  </dependencies>
```

```gradle
dependencies {
    compile 'io.vlingo:vlingo-telemetry:0.7.7'
}

repositories {
    jcenter()
}
```

License (See LICENSE file for full license)
-------------------------------------------
Copyright © 2012-2018 Vaughn Vernon. All rights reserved.

This Source Code Form is subject to the terms of the
Mozilla Public License, v. 2.0. If a copy of the MPL
was not distributed with this file, You can obtain
one at https://mozilla.org/MPL/2.0/.
