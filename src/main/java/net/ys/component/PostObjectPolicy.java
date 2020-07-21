package net.ys.component;

import com.alibaba.fastjson.JSON;
import net.ys.constants.PostObjectConstants;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author nmy
 */
public class PostObjectPolicy {
    private String expiration;
    private List<Object> conditions = new ArrayList<>();

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public List<Object> getConditions() {
        return conditions;
    }

    public void setConditions(List<List<String>> conditions) {
        for (List<String> condition : conditions) {
            if (condition.size() == 2) {
                this.addCondition(condition.get(0), condition.get(1));
                return;
            }

            if (condition.size() == 3) {
                this.addCondition(condition.get(0), condition.get(1), condition.get(2));
                return;
            }

            throw new IllegalArgumentException("the condition size is invalid: " + condition.toString());
        }
    }

    public void addEqualMatchCondition(String key, String value) {
        this.addCondition(key, value);
    }

    /**
     * @param key    这里输入的key不需要加'$'符号
     * @param prefix
     */
    public void addStartWithMatchCondition(String key, String prefix) {
        this.addCondition(PostObjectConstants.STARTS_WITH, "$" + key, prefix);
    }

    public void addContentLengthMatchCondition(long minSize, long maxSize) {
        this.addCondition(PostObjectConstants.CONTENT_LENGTH_RANGE, minSize + "", maxSize + "");
    }

    public void addCondition(String key, String value) {
        Map<String, String> condition = new HashMap<>();
        condition.put(key, value);
        this.conditions.add(condition);
    }

    public void addCondition(String operation, String key, String value) {
        this.conditions.add(Arrays.asList(operation, key, value));
    }

    public String toBase64String() throws UnsupportedEncodingException {
        String jsonPolicy = JSON.toJSONString(this);
        return Base64.encodeBase64String(jsonPolicy.getBytes("utf-8"));
    }
}
