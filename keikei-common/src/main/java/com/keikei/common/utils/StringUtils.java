package com.keikei.common.utils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static final String arr = "{}" ;

    public static String format(String template,Object...args){
        if(isEmpty(args) || isEmpty(template)){
            return template;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int size = args.length;
        int handlerIndex = 0;
        for(int start = 0;start<size;start++){
            int index = template.indexOf(arr,handlerIndex);
            if(index==-1){
                if(handlerIndex == 0){
                    return template;
                }
                else {
                     stringBuilder.append(template,handlerIndex,template.length());
                }

            }
            else {
                stringBuilder.append(template,handlerIndex,index);
                stringBuilder.append(args[start]);
                handlerIndex = index+2;
            }
        }
        stringBuilder.append(template,handlerIndex,template.length());
        return stringBuilder.toString();

    }

    private static boolean isEmpty(Object[] args) {
        return isNull(args)|| args.length==0;
    }

    private static boolean isNull(Object[] args) {
        return args == null;
    }
}
