package com.keikei.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class DateUtils {
    public static String dataPath() {
        Date date = new Date();
        return DateFormatUtils.format(date,"yyyy/MM/dd");
    }
}
