package com.daejong.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * 小工具,
 * 配合属性上的注解来使用
 * @JsonSerialize(using = Date2LongSerializer.class)
 * 可以让时间戳的Date类型转成Long类型, 从而避免 接口返回date类型的时候在数据有效位会添加3个零.
 * Created by Daejong on 2017/9/17.
 */
public class Date2LongSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(date.getTime() / 1000);
    }
}
