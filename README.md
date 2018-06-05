# TicktockMusic

## 介绍
「Ticktock」一款 material design 风格的音乐播放器，可以播放本地和网络音乐，并且提供歌词显示。项目采用 clean architecture、mvp、rxJava2、retrofit2、dagger2 进行开发，代码结构清晰，层次分明。

## 下载
[下载地址](http://fir.im/w3dl) (最低版本：Android 5.0)

本项目衍生的两个自定义View：
- [歌词控件(https://github.com/Lauzy/LyricView)](https://github.com/Lauzy/LyricView)
- [播放暂停按钮(https://github.com/Lauzy/PlayPauseView)](https://github.com/Lauzy/PlayPauseView)

## 预览
<img src="/screenshots/screenshot01.jpg" alt="screenshot" title="screenshot1" width="270" height="460" />  <img src="/screenshots/screenshot02.jpg" alt="screenshot" title="screenshot2" width="270" height="460" /> <img src="/screenshots/screenshot03.jpg" alt="screenshot" title="screenshot3" width="270" height="460" /> <img src="/screenshots/screenshot04.jpg" alt="screenshot" title="screenshot4" width="270" height="460" /> <img src="/screenshots/screenshot05.jpg" alt="screenshot" title="screenshot5" width="270" height="460" />

## 博客介绍
- 项目架构：[https://www.jianshu.com/p/15ea0fecb61d](https://www.jianshu.com/p/15ea0fecb61d)
- 开源库封装：[https://www.jianshu.com/p/1645b81dc994](https://www.jianshu.com/p/1645b81dc994)
- 自定义播放暂停按钮：[https://www.jianshu.com/p/74f38e9b16fc](https://www.jianshu.com/p/74f38e9b16fc)
- 自定义歌词View: [https://www.jianshu.com/p/ab735509cc74](https://www.jianshu.com/p/ab735509cc74)

## 特点
- Material design
- MediaSession + MediaController 控制音乐播放
- 本地音乐 + 网络歌曲
- 播放队列
- 文件夹、我的喜欢、最近播放、搜索等
- 支持歌词显示及同步
- 耳机线控、通话及多音频播放时暂停
- 动态换肤

## 感谢
- [RxJava](https://github.com/ReactiveX/RxJava)、[Retrofit](https://github.com/square/retrofit)、[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)、
[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)、[MagicaSakura](https://github.com/Bilibili/MagicaSakura) 等
- [LastFmApi](https://www.last.fm/api)、[百度音乐Api](https://www.jianshu.com/p/a6718b11fdf1) 、[歌词迷Api](https://github.com/solos/geci.me-api)
- [Timber](https://github.com/naman14/Timber)

## License

```
Copyright (c) 2017-present Lauzy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```