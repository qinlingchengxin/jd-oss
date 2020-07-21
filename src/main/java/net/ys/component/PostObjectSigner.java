package net.ys.component;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.util.BinaryUtils;
import net.ys.constants.PostObjectConstants;

import java.util.Map;

/**
 * @author nmy
 */
public class PostObjectSigner extends AWS4Signer {

    public String sign(AWSCredentials awsCredentials, Map<String, String> formFields) {
        final String stringToSign = formFields.get(PostObjectConstants.POLICY);
        String credentialStr = formFields.get(PostObjectConstants.X_AMZ_CREDENTIAL);
        String[] credentialParts = credentialStr.split("/");
        if (credentialParts.length != 5) {
            throw new IllegalArgumentException("the X-Amz-Credential is illegal, value: " + credentialStr);
        }
        final byte[] signingKey = newSigningKey(awsCredentials, credentialParts[1], credentialParts[2], PostObjectConstants.S3_SERVICE);

        final byte[] signature = computeSignature(stringToSign, signingKey, null);
        return BinaryUtils.toHex(signature);
    }
}
