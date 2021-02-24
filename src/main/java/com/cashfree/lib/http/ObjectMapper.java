package com.cashfree.lib.http;

import com.alibaba.fastjson.JSON;

import com.cashfree.lib.utils.CommonUtils;

public class ObjectMapper {
  public static <T> T readValue(String str, Class<T> clazz) {
    if (CommonUtils.isBlank(str)) return null;
    return JSON.parseObject(str, clazz);
  }

  public static <T> String write(T t) {
    if (t == null) return null;

    if (t instanceof String) return (String) t;

    return JSON.toJSONString(t);
  }
}
