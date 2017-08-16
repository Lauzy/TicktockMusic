package com.freedom.lauzy.ticktockmusic.injection.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Desc : PerFragment
 * Author : Lauzy
 * Date : 2017/7/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}
