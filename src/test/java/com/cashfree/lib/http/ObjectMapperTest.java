package com.cashfree.lib.http;

import com.cashfree.lib.payout.domains.response.AuthenticationResponse;

public class ObjectMapperTest {
  public static void main(String[] args) {
    new ObjectMapperTest().testObjectMapperWithInheritance();
  }

  public void testObjectMapperWithInheritance() {
    String str = "{\"status\":\"SUCCESS\",\"subCode\":\"200\",\"message\":\"Token generated\",\"data\":{\"token\":\"IIII\",\"expiry\":1580902627}}";

    AuthenticationResponse authResponse = ObjectMapper.readValue(str, AuthenticationResponse.class);
    AuthenticationResponse expectedObj =
        new AuthenticationResponse()
            .setData(
                new AuthenticationResponse.Payload()
                    .setToken("IIII")
                    .setExpiry(1580902627L));
    expectedObj.setStatus("SUCCESS").setSubCode(200).setMessage("Token generated");

    System.out.println(expectedObj.equals(authResponse));
  }
}
