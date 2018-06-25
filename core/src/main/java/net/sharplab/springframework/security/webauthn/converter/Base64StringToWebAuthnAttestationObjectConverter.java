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

package net.sharplab.springframework.security.webauthn.converter;

import com.webauthn4j.attestation.AttestationObject;
import com.webauthn4j.converter.AttestationObjectConverter;
import org.springframework.core.convert.converter.Converter;

/**
 * Spring converter which converts from Base64{@link String} to {@link AttestationObject}
 */
public class Base64StringToWebAuthnAttestationObjectConverter implements Converter<String, AttestationObject> {

    //~ Instance fields
    // ================================================================================================
    private AttestationObjectConverter converter = new AttestationObjectConverter();

    @Override
    public AttestationObject convert(String source) {
        return converter.convert(source);
    }

}
