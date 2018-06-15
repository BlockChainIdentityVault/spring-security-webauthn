/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sharplab.springframework.security.webauthn.server;

import com.webauthn4j.client.Origin;
import com.webauthn4j.client.challenge.Challenge;
import com.webauthn4j.server.ServerProperty;
import net.sharplab.springframework.security.webauthn.challenge.ChallengeRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@inheritDoc}
 */
public class ServerPropertyProviderImpl implements ServerPropertyProvider {

    //~ Instance fields
    // ================================================================================================
    private String rpId = null;
    private ChallengeRepository challengeRepository;

    public ServerPropertyProviderImpl(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    public ServerProperty provide(HttpServletRequest request, HttpServletResponse response) {

        Origin origin = obtainOrigin(request);
        Challenge savedChallenge = obtainSavedChallenge(request);

        String rpId = origin.getHost();
        if (this.rpId != null) {
            rpId = this.rpId;
        }

        return new ServerProperty(origin, rpId, savedChallenge, null); // tokenBinding is not supported by Servlet API as of 4.0
    }

    public String getRpId() {
        return rpId;
    }

    public void setRpId(String rpId) {
        this.rpId = rpId;
    }


    private Origin obtainOrigin(HttpServletRequest request) {
        return new Origin(request.getScheme(), request.getServerName(), request.getServerPort());
    }

    private Challenge obtainSavedChallenge(HttpServletRequest request) {
        return challengeRepository.loadChallenge(request);
    }
}
