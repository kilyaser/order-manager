package com.profcut.ordermanager.common.consts;

public final class DataTypes {

    public static final int STRING_LENGTH_MAX = 255;
    public static final int UUID_LENGTH = 36;
    public static final int ARRAY_MAX_ITEMS_VALUE = 500;
    public static final int DATE_LENGTH = 10;
    public static final int DATE_TIME_LENGTH = 19;
    public static final String MINIMUM_VALUE = "0";
    public static final String MAXIMUM_LONG_VALUE = "9223372036854775807";

    private DataTypes() {
        throw new UnsupportedOperationException("This class cannot be instantiated, it is a utility class");
    }
}
