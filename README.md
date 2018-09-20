# 基于MVP+RxJava+Retrofit的电影资源聚合APP
看电影不用再去PC端下载了，移动端一样可以。

![](https://github.com/hiliving/superMovie/blob/master/picture/Screenshot_2018-03-02-18-01-25.png)
![](https://github.com/hiliving/superMovie/blob/master/picture/Screenshot_2018-03-02-18-01-31.png)
![](https://github.com/hiliving/superMovie/blob/master/picture/Screenshot_2018-03-02-18-01-36.png)
![](https://github.com/hiliving/superMovie/blob/master/picture/Screenshot_2018-03-02-18-01-43.png)

数据是用scrapy爬虫爬来的，不用可惜，就做个APP吧，页面比较简单，但是功能很全，可边下边播，多任务下载。
可自定义添加下载任务，支持种子文件、磁力链接、电驴。
暂时没有提供百度网盘资源的下载功能，这里只显示其下载地址，可复制粘贴到百度网盘客户端去下载。

Tips:
    1.如果你自己写了个APP，依赖了thunder，出现能获取文件大小，无法下载的情况，可能是因为APP的manifest没申明
    文件读写权限。虽然thunder内申明了，但是还是需要APP里再次声明。注意！

即将完成：
  热门视频每天更新，这个主要是后端的定时任务了，目前有点小问题，暂时没有这个功能。

