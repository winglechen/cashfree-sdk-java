package com.cashfree.lib.payout.domains.response;

import java.util.List;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import com.cashfree.lib.utils.CommonUtils;
import com.cashfree.lib.annotations.Deserialize;
import com.cashfree.lib.payout.domains.TransferDetails;
import com.cashfree.lib.serializers.JsonFieldDeserializer;

@Data
@Accessors(chain = true)
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper = true)
public class GetTransfersResponse extends CfPayoutsResponse {
  private Payload data;

  @Data
  @Accessors(chain = true)
  public static final class Payload {
    @Deserialize(using = ListDeserializer.class)
    private List<TransferDetails> transfers;

    private String lastReturnId;
  }

  public static final class ListDeserializer {
    public ListDeserializer() {}

    public List<TransferDetails> deserialize(String serializedString) {
      if (CommonUtils.isBlank(serializedString)) {
        return null;
      }


      return JSON.parseArray(serializedString, TransferDetails.class);
    }
  }
}
