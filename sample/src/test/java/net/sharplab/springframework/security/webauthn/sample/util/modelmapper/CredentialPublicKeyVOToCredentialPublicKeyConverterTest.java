package net.sharplab.springframework.security.webauthn.sample.util.modelmapper;

import com.webauthn4j.attestation.authenticator.Curve;
import com.webauthn4j.attestation.authenticator.ECCredentialPublicKey;
import com.webauthn4j.attestation.statement.COSEAlgorithmIdentifier;
import com.webauthn4j.attestation.statement.COSEKeyType;
import net.sharplab.springframework.security.webauthn.sample.domain.config.ModelMapperConfig;
import net.sharplab.springframework.security.webauthn.sample.domain.vo.ECCredentialPublicKeyVO;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for CredentialPublicKeyVOToCredentialPublicKeyConverter
 */
public class CredentialPublicKeyVOToCredentialPublicKeyConverterTest {

    @Test
    public void mapToExistingInstance_test(){
        ModelMapper modelMapper = ModelMapperConfig.createModelMapper();

        //Given
        ECCredentialPublicKeyVO source = new ECCredentialPublicKeyVO();
        source.setKeyType(COSEKeyType.EC2);
        source.setAlgorithm(COSEAlgorithmIdentifier.ES256);
        source.setCurve(Curve.SECP256R1);
        source.setX(new byte[]{0x00, 0x01});
        source.setY(new byte[]{0x02, 0x03});
        ECCredentialPublicKey destination = new ECCredentialPublicKey();

        //When
        modelMapper.map(source, destination);

        //Then
        assertThat(destination).hasFieldOrPropertyWithValue("keyType", COSEKeyType.EC2);
        assertThat(destination).hasFieldOrPropertyWithValue("algorithm", COSEAlgorithmIdentifier.ES256);
        assertThat(destination).hasFieldOrPropertyWithValue("curve", Curve.SECP256R1);
        assertThat(destination).hasFieldOrPropertyWithValue("x", new byte[]{0x00, 0x01});
        assertThat(destination).hasFieldOrPropertyWithValue("y", new byte[]{0x02, 0x03});
    }
}
