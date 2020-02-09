package com.kangjusang.musicismylife;

import java.util.Map;
import java.util.TreeMap;

public class Utils {

    public static <K, V> V mappedValue(TreeMap<K, V> map, K key) {
        Map.Entry<K, V> e = map.floorEntry(key);
        if (e != null && e.getValue() == null) {
            e = map.lowerEntry(key);
        }
        return e == null ? null : e.getValue();
    }

}
