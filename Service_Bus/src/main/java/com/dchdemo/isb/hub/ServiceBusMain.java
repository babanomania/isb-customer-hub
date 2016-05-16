/**
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
package com.dchdemo.isb.hub;

import org.apache.camel.main.Main;

import com.dchdemo.isb.hub.config.ServiceBusConfigFactory;
import com.dchdemo.isb.hub.dynamics.AccountsBeanFactory;
import com.dchdemo.isb.hub.routes.MasterRouteBuilder;

public final class ServiceBusMain {

    private ServiceBusMain() {
        // to comply with checkstyle rule
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.bind("configFactory", new ServiceBusConfigFactory());
        main.bind("accountsBeanFactory", new AccountsBeanFactory());
        main.addRouteBuilder(new MasterRouteBuilder());
        main.run();
    }
}
