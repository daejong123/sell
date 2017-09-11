package com.daejong.VO;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * Created by Daejong on 2017/9/10.
 */
@Data
public class ResultVO<T>{

    //错误码
    private Integer code;

    //提示信息
    private String msg;

    //返回的具体数据内容
    private T data;

}
