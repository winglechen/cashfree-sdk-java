package com.cashfree.lib.payout.domains.response;

import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import com.cashfree.lib.utils.CommonUtils;
import com.cashfree.lib.constants.Constants;
import com.cashfree.lib.annotations.Serialize;
import com.cashfree.lib.annotations.Deserialize;

@Data
@Accessors(chain = true)
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper = true)
public class BatchTransferStatusResponse extends CfPayoutsResponse {
  private static final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
      .appendPattern("yyyy-MM-dd")
      .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
      .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
      .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
      .toFormatter();

  private Payload data;

  @Data
  @Accessors(chain = true)
  public static final class Payload {
    private String rowCount;

    private Integer referenceId;

    @Deserialize(using = ListDeserializer.class)
    private List<Transfer> transfers;

    @Data
    @Accessors(chain = true)
    public static final class Transfer {
      private String beneId;

      private String transferId;

      private String referenceId;

      private String bankAccount;

      private String ifsc;

      private BigDecimal amount;

      private String remarks;

      private String status;

      private String utr;

      @Serialize(using = DateSerializer.class)
      @Deserialize(using = DateDeserializer.class)
      private LocalDateTime addedOn;

      @Serialize(using = DateSerializer.class)
      @Deserialize(using = DateDeserializer.class)
      private LocalDateTime processedOn;

      private String failureReason;
    }
  }

  public static final class DateSerializer  {
    public DateSerializer() {}

    public String serialize(LocalDateTime value) {
      if (value == null) {
        return null;
      } else {
        return dateTimeFormatter.format(value);
      }
    }
  }

  public static final class DateDeserializer  {
    public DateDeserializer() {}

    public LocalDateTime deserialize(String dateAsString) {
      if (CommonUtils.isBlank(dateAsString) || Constants.PLACEHOLDER_DATESTRING.equals(dateAsString)) {
        return null;
      }

      return LocalDateTime.parse(dateAsString, dateTimeFormatter);
    }
  }

  public static final class ListDeserializer  {
    public ListDeserializer() {}

    public List<Payload.Transfer> deserialize(String serializedString) {
      if (CommonUtils.isBlank(serializedString)) {
        return null;
      }

      return JSON.parseArray(serializedString, Payload.Transfer.class);
    }
  }
}

