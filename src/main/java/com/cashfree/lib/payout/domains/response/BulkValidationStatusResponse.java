package com.cashfree.lib.payout.domains.response;

import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import com.cashfree.lib.utils.CommonUtils;
import com.cashfree.lib.annotations.Deserialize;

@Data
@Accessors(chain = true)
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper = true)
public class BulkValidationStatusResponse extends CfPayoutsResponse {
  private Payload data;

  @Data
  @Accessors(chain = true)
  public static final class Payload {
    private String bulkValidationId;

    @Deserialize(using = CustomMapDeserializer.class)
    private Map<String, Entry> entries;

    @Data
    @Accessors(chain = true)
    public static final class Entry {
      private String name;

      private String phone;

      private String bankAccount;

      private String ifsc;

      private String accountExists;

      private String nameAtBank;

      private BigDecimal amountDeposited;

      private String utr;

      private String refId;

      private String message;
    }
  }

  public static final class CustomMapDeserializer {
    public CustomMapDeserializer() {}

    public Map<String, Payload.Entry> deserialize(String serializedString) {
      if (CommonUtils.isBlank(serializedString)) {
        return null;
      }

      Map<String, Payload.Entry> map = new HashMap<>();
      JSONObject json = JSON.parseObject(serializedString);
      for (String key : json.keySet() ) {
        map.put(key, json.getObject(key, Payload.Entry.class));
      }

      return map;
    }
  }
}
