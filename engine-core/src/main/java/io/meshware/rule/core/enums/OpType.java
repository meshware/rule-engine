package io.meshware.rule.core.enums;

/**
 * Operate Type
 *
 * @author Zhiguo.Chen
 */
public enum OpType {
    EQ("eq", "等于"),
    NE("ne", "不等于"),
    OR("or", "或者"),
    NIN("nin", "不包含"),
    IN("in", "包含"),
    BTW("between", "区间"),
    RANGE("range", "多区间段");

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    OpType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static OpType getOpTypeByCode(String code) {
        for (OpType opType : values()) {
            if (opType.getCode().equals(code)) {
                return opType;
            }
        }
        return null;
    }

}
