/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.spring.boot;

import org.apache.camel.component.properties.DefaultPropertiesParser;
import org.springframework.core.env.Environment;

/**
 * Extension to {@link DefaultPropertiesParser} that will lookup in Spring via {@link Environment#getProperty(String)}.
 */
public class SpringPropertiesParser extends DefaultPropertiesParser {

    // Members
    private final Environment env;

    public SpringPropertiesParser(Environment env) {
        this.env = env;
    }

    @Override
    public String customLookup(String key) {
        return env.getProperty(key);
    }

}
