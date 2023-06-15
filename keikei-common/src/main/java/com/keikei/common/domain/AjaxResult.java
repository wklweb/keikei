package com.keikei.common.domain;

import com.keikei.common.constants.HttpStatus;

import java.util.HashMap;

public class AjaxResult extends HashMap<String,Object> {
    private static final long serialVersionUID = 1L;
    public static final String REQUEST_CODE = "code";
    public static final String DATA = "data";
    public static final String MSG = "msg";


    public AjaxResult() {
    }

    public AjaxResult(int code,String msg,Object data){
        super.put(REQUEST_CODE,code);
        super.put(MSG,msg);
        if(data!=null){
            super.put(DATA,data);
        }
    }
    public AjaxResult(int code,String msg){
        super.put(REQUEST_CODE,code);
        super.put(MSG,msg);
    }
    public AjaxResult(String msg){
        super.put(MSG,msg);
    }

    public static AjaxResult success(String msg,Object data){
        return new AjaxResult(HttpStatus.success,msg,data);
    }

    public static AjaxResult success(){
        return success(null,null);
    }
    public static AjaxResult success(String msg) {
        return success(msg,null);
    }
    public static AjaxResult warn(String msg,String data){
        return new AjaxResult(HttpStatus.warning,msg,data);
    }
    public static AjaxResult warn(String msg){
        return warn(msg,null);
    }
    public static AjaxResult error(int code,String msg,String data){
        return new AjaxResult(code,msg,data);
    }
    public static AjaxResult error(String msg,String data){
        return error(HttpStatus.error,msg,data);
    }
    public static AjaxResult error(int code,String msg){
        return error(code,msg,null);
    }
    public static AjaxResult error(String msg){
        return error(msg,null);
    }


}
