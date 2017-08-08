package com.lauzy.freedom.librarys.imageload;

import android.content.Context;

/**
 * Desc : image loader strategy interface
 * Author : Lauzy
 * Date : 2017/6/30
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface IBaseImageStrategy {

    void display(Context context, ImageConfig imageConfig);

    void clean(Context context , ImageConfig imageConfig);
}
