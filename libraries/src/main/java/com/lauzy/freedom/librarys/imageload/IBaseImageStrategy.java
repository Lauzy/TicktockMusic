package com.lauzy.freedom.librarys.imageload;

import android.content.Context;

/**
 * Desc : 基础策略接口，此处也可可采用泛型，子类继承ImageConfig来实现，此处暂不使用。
 * 因为若三方库改动，则子类Config变动太大。故认为直接使用ImageConfig即可，可自行斟酌。
 * 代码如下：
 * public interface IBaseImageStrategy<T extends ImageConfig> {
 *     void display(Context context, T imageConfig);}
 * Author : Lauzy
 * Date : 2017/6/30
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface IBaseImageStrategy {

    void display(Context context, ImageConfig imageConfig);

    void clean(Context context, ImageConfig imageConfig);
}
