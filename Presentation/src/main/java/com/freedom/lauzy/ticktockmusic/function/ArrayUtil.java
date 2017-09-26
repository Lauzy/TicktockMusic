package com.freedom.lauzy.ticktockmusic.function;

/**
 * Desc : 数组工具
 * Author : Lauzy
 * Date : 2017/9/26
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ArrayUtil {

    public static String[] insertArr(String[] arr, int index, String value) {
        if (arr != null && arr.length != 0) {
            String[] newArr = new String[arr.length + 1];
            for (int i = 0; i < arr.length; i++) {
                newArr[i] = arr[i];
            }
            for (int i = newArr.length - 1; i > index; i--) {
                newArr[i] = newArr[i - 1];
            }
//            System.arraycopy(arr, 0, newArr, 0, arr.length);
//            System.arraycopy(newArr, index, newArr, index + 1, newArr.length - 1 - index);
            newArr[index] = value;
            return newArr;
        } else {
            return new String[]{value};
        }
    }
}
