package com.chinmayshivratriwar.expense_tracker.util;

import com.chinmayshivratriwar.expense_tracker.constants.Constant;

public class ControllerUtil {

    public static String getAccessToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith(Constant.BEARER)) {
            return Constant.EMPTY_STRING;
        }
        return authHeader.substring(7);
    }
}
