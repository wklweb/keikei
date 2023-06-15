package com.keikei.common.constants;

public class UserConstants {
    public static String usernameRegex = "^[\u4e00-\u9fa5a-zA-Z0-9]{6,12}$";
    public static String passwordRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,20}$";
}
