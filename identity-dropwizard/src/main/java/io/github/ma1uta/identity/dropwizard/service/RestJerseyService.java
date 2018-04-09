/*
 * Copyright sablintolya@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ma1uta.identity.dropwizard.service;

import io.github.ma1uta.identity.service.RestService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;

public class RestJerseyService implements RestService {

    private final Client client;

    public RestJerseyService(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public <Q, P> P post(String url, Q request) {
        getClient().target(url).request().post(Entity.json(request));
        return null;
    }
}