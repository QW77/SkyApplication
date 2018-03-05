package com.sky.app.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mc on 2017/12/6.
 */

public class ListUtils {

    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分
     *
     * @param list 集合
     * @param n    分成几部分
     * @return 返回一个集合的集合。分成的n个集合都在这个最外层集合中
     */
    public static List<List<?>> splitList(List<?> list, int n) {
        if (list == null || list.size() == 0 || n < 1) {
            return null;
        }
        List<List<?>> result = new ArrayList<List<?>>();
        int size = list.size();
        int count = (size + n - 1) / n;
        for (int i = 0; i < count; i++) {
            List<?> subList = list.subList(i * n, ((i + 1) * n > size ? size : n * (i + 1)));
            result.add(subList);
        }
        return result;
    }
}
