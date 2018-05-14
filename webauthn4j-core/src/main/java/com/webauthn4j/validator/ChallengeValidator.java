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

package com.webauthn4j.validator;

import com.webauthn4j.client.CollectedClientData;
import com.webauthn4j.client.challenge.Challenge;
import com.webauthn4j.rp.RelyingParty;
import com.webauthn4j.validator.exception.BadChallengeException;
import com.webauthn4j.validator.exception.MissingChallengeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Validates {@link Challenge} instance
 */
class ChallengeValidator {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public void validate(CollectedClientData collectedClientData, RelyingParty relyingParty) {
        if(collectedClientData == null){
            throw new IllegalArgumentException("collectedClientData must not be null");
        }
        if(relyingParty == null){
            throw new IllegalArgumentException("relyingParty must not be null");
        }
        Challenge savedChallenge = relyingParty.getChallenge();
        Challenge collectedChallenge = collectedClientData.getChallenge();

        if (savedChallenge == null) {
            logger.debug("Authentication failed: challenge is not found in the relying party");
            throw new MissingChallengeException("Missing challenge");
        }

        // Verify that the challenge member of the collectedClientData matches the challenge that was sent to
        // the authenticator in the PublicKeyCredentialRequestOptions passed to the get() call.
        validate(savedChallenge, collectedChallenge);

    }

    public void validate(Challenge expected, Challenge actual){
        if(expected == null){
            throw new IllegalArgumentException("expected must not be null");
        }
        if(actual == null){
            throw new IllegalArgumentException("actual must not be null");
        }
        byte[] expectedChallengeBytes = expected.getValue();
        byte[] actualChallengeBytes = actual.getValue();

        if (!Arrays.equals(expectedChallengeBytes, actualChallengeBytes)) {
            logger.debug("Authentication failed: bad challenge is specified");
            throw new BadChallengeException("Bad challenge");
        }
    }
}