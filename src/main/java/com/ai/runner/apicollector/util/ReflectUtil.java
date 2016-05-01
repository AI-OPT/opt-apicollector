package com.ai.runner.apicollector.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectUtil {

    // 存储参数类型的值是否支持展开
    private static Map<String, Boolean> type2Map = new HashMap<String, Boolean>();

    static {
        type2Map.put(String.class.getName(), true);
        type2Map.put(Long.class.getName(), true);
        type2Map.put(Integer.class.getName(), true);
        type2Map.put(Boolean.class.getName(), true);
        type2Map.put(Double.class.getName(), true);
        type2Map.put(Character.class.getName(), true);
        type2Map.put(Byte.class.getName(), true);
        type2Map.put(Short.class.getName(), true);
        type2Map.put("boolean", true);
        type2Map.put("byte", true);
        type2Map.put("char", true);
        type2Map.put("double", true);
        type2Map.put("float", true);
        type2Map.put("int", true);
        type2Map.put("long", true);
        type2Map.put("short", true);
        type2Map.put("void", true);
        type2Map.put(HashMap.class.getName(), true);
        type2Map.put(Map.class.getName(), true);
        type2Map.put(List.class.getName(), true);
        type2Map.put(ArrayList.class.getName(), true);
        type2Map.put(java.sql.Date.class.getName(), true);
        type2Map.put(java.util.Date.class.getName(), true);
        type2Map.put(java.sql.Timestamp.class.getName(), true);
    }

    /**
     * 参数类型是否支持展开。一般对于VO对象才可以，基本类型不需要继续查看
     * 
     * @param pType
     * @return
     * @author zhangchao
     */
    public static boolean checkBaseType(String pType) {
        return type2Map.containsKey(pType) ? type2Map.get(pType) : false;
    }

}
