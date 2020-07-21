package net.ys.controller;

import net.ys.config.OssConfig;
import net.ys.constants.PostObjectConstants;
import net.ys.service.OssService;
import net.ys.util.TimeUtil;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: NMY
 * @Date: 2020/7/21
 * @Time: 15:35
 */
@Controller
public class OssController {

    @Autowired
    private OssConfig ossConfig;

    @Autowired
    private OssService ossService;

    @GetMapping("/init")
    @ResponseBody
    public Object init(String ext) throws UnsupportedEncodingException {

        Date date = new Date();
        String now = TimeUtil.getDate(date);
        Map<String, String> formFields = new CaseInsensitiveMap();
        formFields.put(PostObjectConstants.KEY, TimeUtil.getDate(new Date()) + "/" + UUID.randomUUID().toString() + "." + ext);
        formFields.put(PostObjectConstants.POLICY, ossService.genPolicy(date));
        formFields.put(PostObjectConstants.SUCCESS_ACTION_STATUS, "201");
        formFields.put(PostObjectConstants.X_AMZ_CREDENTIAL, ossConfig.getAccessKey() + "/" + now + "/" + ossConfig.getRegion() + "/s3/aws4_request");
        formFields.put(PostObjectConstants.X_AMZ_ALGORITHM, "AWS4-HMAC-SHA256");
        formFields.put(PostObjectConstants.X_AMZ_DATE, TimeUtil.getISO8601Timestamp(date));

        String signature = ossService.genSignature(formFields);
        Map<String, String> data = new HashMap<>(8);
        data.put("key", formFields.get("key"));
        data.put("policy", formFields.get("policy"));
        data.put("status", "201");
        data.put("credential", formFields.get("X-Amz-Credential"));
        data.put("algorithm", "AWS4-HMAC-SHA256");
        data.put("amz", formFields.get("X-Amz-Date"));
        data.put("signature", signature);
        return data;
    }

    @GetMapping("/")
    public String upload(Model model) {
        model.addAttribute("action", "https://" + ossConfig.getBucketName() + ".s3.cn-north-1.jcloudcs.com");
        return "upload";
    }
}
