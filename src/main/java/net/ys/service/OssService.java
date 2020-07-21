package net.ys.service;

import com.amazonaws.auth.AWSCredentials;
import net.ys.component.PostObjectPolicy;
import net.ys.component.PostObjectSigner;
import net.ys.config.OssConfig;
import net.ys.constants.PostObjectConstants;
import net.ys.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * @author: NMY
 * @Date: 2020/7/21
 * @Time: 15:44
 */
@Service
public class OssService {

    @Autowired
    private AWSCredentials credentials;

    @Autowired
    private OssConfig ossConfig;

    @Autowired
    private PostObjectSigner postObjectSigner;

    /**
     * 获取签名
     *
     * @param formFields
     * @return
     */
    public String genSignature(Map<String, String> formFields) {
        return postObjectSigner.sign(credentials, formFields);
    }

    /**
     * 获取policy
     *
     * @return
     */
    public String genPolicy(Date date) throws UnsupportedEncodingException {

        String now = TimeUtil.getDate(date);

        PostObjectPolicy policy = new PostObjectPolicy();
        policy.setExpiration("2121-07-30T12:00:00.000Z");
        policy.addEqualMatchCondition(PostObjectConstants.BUCKET, ossConfig.getBucketName());
        policy.addStartWithMatchCondition(PostObjectConstants.KEY, now + "/");

        policy.addEqualMatchCondition(PostObjectConstants.SUCCESS_ACTION_STATUS, "201");
        policy.addEqualMatchCondition(PostObjectConstants.X_AMZ_CREDENTIAL, ossConfig.getAccessKey() + "/" + now + "/" + ossConfig.getRegion() + "/s3/aws4_request");
        policy.addEqualMatchCondition(PostObjectConstants.X_AMZ_ALGORITHM, "AWS4-HMAC-SHA256");
        policy.addEqualMatchCondition(PostObjectConstants.X_AMZ_DATE, TimeUtil.getISO8601Timestamp(date));
        policy.addContentLengthMatchCondition(1, ossConfig.getMaxSize());
        return policy.toBase64String();
    }

}
