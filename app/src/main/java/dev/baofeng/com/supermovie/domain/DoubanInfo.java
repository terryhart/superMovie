package dev.baofeng.com.supermovie.domain;

import java.util.List;

/**
 * creator huangyong
 * createTime 2018/12/21 下午2:44
 * path dev.baofeng.com.supermovie.domain
 * description:获取豆瓣电影正在上映的100条
 */
public class DoubanInfo {


    /**
     * count : 20
     * start : 0
     * total : 36
     * subjects : [{"rating":{"max":10,"average":0,"stars":"00","min":0},"genres":["喜剧","奇幻"],"title":"天气预爆","casts":[{"alt":"https://movie.douban.com/celebrity/1274979/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg"},"name":"肖央","id":"1274979"},{"alt":"https://movie.douban.com/celebrity/1323516/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1368850348.93.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1368850348.93.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1368850348.93.jpg"},"name":"杜鹃","id":"1323516"},{"alt":"https://movie.douban.com/celebrity/1327084/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1363597076.12.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1363597076.12.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1363597076.12.jpg"},"name":"常远","id":"1327084"}],"collect_count":1681,"original_title":"天气预爆","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1274979/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg"},"name":"肖央","id":"1274979"}],"year":"2018","images":{"small":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541281178.jpg","large":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541281178.jpg","medium":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541281178.jpg"},"alt":"https://movie.douban.com/subject/26994789/","id":"26994789"},{"rating":{"max":10,"average":9,"stars":"45","min":0},"genres":["动作","科幻","动画"],"title":"蜘蛛侠：平行宇宙","casts":[{"alt":"https://movie.douban.com/celebrity/1350106/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1434437756.07.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1434437756.07.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1434437756.07.jpg"},"name":"沙梅克·摩尔","id":"1350106"},{"alt":"https://movie.douban.com/celebrity/1316713/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1449582908.84.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1449582908.84.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1449582908.84.jpg"},"name":"杰克·约翰逊","id":"1316713"},{"alt":"https://movie.douban.com/celebrity/1312964/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p20419.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p20419.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p20419.jpg"},"name":"海莉·斯坦菲尔德","id":"1312964"}],"collect_count":22648,"original_title":"Spider-Man: Into the Spider-Verse","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1310107/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1519064730.28.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1519064730.28.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1519064730.28.jpg"},"name":"鲍勃·佩尔西凯蒂","id":"1310107"},{"alt":"https://movie.douban.com/celebrity/1324415/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p59042.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p59042.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p59042.jpg"},"name":"彼得·拉姆齐","id":"1324415"},{"alt":"https://movie.douban.com/celebrity/1296189/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1543307159.85.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1543307159.85.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1543307159.85.jpg"},"name":"罗德尼·罗斯曼","id":"1296189"}],"year":"2018","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2542867516.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2542867516.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2542867516.jpg"},"alt":"https://movie.douban.com/subject/26374197/","id":"26374197"},{"rating":{"max":10,"average":0,"stars":"00","min":0},"genres":["喜剧","奇幻","武侠"],"title":"武林怪兽","casts":[{"alt":"https://movie.douban.com/celebrity/1027577/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1421047436.79.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1421047436.79.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1421047436.79.jpg"},"name":"古天乐","id":"1027577"},{"alt":"https://movie.douban.com/celebrity/1326363/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1508145845.01.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1508145845.01.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1508145845.01.jpg"},"name":"陈学冬","id":"1326363"},{"alt":"https://movie.douban.com/celebrity/1274514/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1379398896.7.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1379398896.7.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1379398896.7.jpg"},"name":"郭碧婷","id":"1274514"}],"collect_count":891,"original_title":"武林怪兽","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1106979/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1403267018.07.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1403267018.07.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1403267018.07.jpg"},"name":"刘伟强","id":"1106979"}],"year":"2018","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2540374845.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2540374845.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2540374845.jpg"},"alt":"https://movie.douban.com/subject/26425062/","id":"26425062"},{"rating":{"max":10,"average":7.9,"stars":"40","min":0},"genres":["动作","奇幻","冒险"],"title":"海王","casts":[{"alt":"https://movie.douban.com/celebrity/1022614/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p32853.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p32853.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p32853.jpg"},"name":"杰森·莫玛","id":"1022614"},{"alt":"https://movie.douban.com/celebrity/1044702/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34697.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34697.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34697.jpg"},"name":"艾梅柏·希尔德","id":"1044702"},{"alt":"https://movie.douban.com/celebrity/1010539/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p9206.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p9206.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p9206.jpg"},"name":"威廉·达福","id":"1010539"}],"collect_count":410004,"original_title":"Aquaman","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1032122/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509950363.8.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509950363.8.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1509950363.8.jpg"},"name":"温子仁","id":"1032122"}],"year":"2018","images":{"small":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541280047.jpg","large":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541280047.jpg","medium":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541280047.jpg"},"alt":"https://movie.douban.com/subject/3878007/","id":"3878007"},{"rating":{"max":10,"average":0,"stars":"00","min":0},"genres":["动作"],"title":"叶问外传：张天志","casts":[{"alt":"https://movie.douban.com/celebrity/1318005/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1436716618.28.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1436716618.28.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1436716618.28.jpg"},"name":"张晋","id":"1318005"},{"alt":"https://movie.douban.com/celebrity/1014003/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1493202154.34.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1493202154.34.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1493202154.34.jpg"},"name":"戴夫·巴蒂斯塔","id":"1014003"},{"alt":"https://movie.douban.com/celebrity/1312846/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p39129.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p39129.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p39129.jpg"},"name":"柳岩","id":"1312846"}],"collect_count":1719,"original_title":"葉問外傳：張天志","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1275026/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p9332.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p9332.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p9332.jpg"},"name":"袁和平","id":"1275026"}],"year":"2018","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2542380253.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2542380253.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2542380253.jpg"},"alt":"https://movie.douban.com/subject/26796664/","id":"26796664"},{"rating":{"max":10,"average":9.1,"stars":"45","min":0},"genres":["动画","奇幻","冒险"],"title":"龙猫","casts":[{"alt":"https://movie.douban.com/celebrity/1019382/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1455201170.02.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1455201170.02.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1455201170.02.jpg"},"name":"日高法子","id":"1019382"},{"alt":"https://movie.douban.com/celebrity/1025582/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29537.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29537.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p29537.jpg"},"name":"坂本千夏","id":"1025582"},{"alt":"https://movie.douban.com/celebrity/1379738/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503457262.72.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503457262.72.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503457262.72.jpg"},"name":"糸井重里","id":"1379738"}],"collect_count":794686,"original_title":"となりのトトロ","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1054439/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p616.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p616.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p616.jpg"},"name":"宫崎骏","id":"1054439"}],"year":"1988","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2540924496.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2540924496.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2540924496.jpg"},"alt":"https://movie.douban.com/subject/1291560/","id":"1291560"},{"rating":{"max":10,"average":4.2,"stars":"25","min":0},"genres":["剧情"],"title":"中国合伙人2","casts":[{"alt":"https://movie.douban.com/celebrity/1312817/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1515991360.72.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1515991360.72.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1515991360.72.jpg"},"name":"赵立新","id":"1312817"},{"alt":"https://movie.douban.com/celebrity/1274651/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1500735770.38.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1500735770.38.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1500735770.38.jpg"},"name":"凌潇肃","id":"1274651"},{"alt":"https://movie.douban.com/celebrity/1367682/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1501833870.86.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1501833870.86.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1501833870.86.jpg"},"name":"王嘉","id":"1367682"}],"collect_count":4033,"original_title":"中国合伙人2","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1406262/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1544078893.81.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1544078893.81.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1544078893.81.jpg"},"name":"刘亚当","id":"1406262"}],"year":"2018","images":{"small":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541742368.jpg","large":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541742368.jpg","medium":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541742368.jpg"},"alt":"https://movie.douban.com/subject/30293828/","id":"30293828"},{"rating":{"max":10,"average":7.7,"stars":"40","min":0},"genres":["剧情","喜剧","传记"],"title":"印度合伙人","casts":[{"alt":"https://movie.douban.com/celebrity/1049615/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p7474.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p7474.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p7474.jpg"},"name":"阿克谢·库玛尔","id":"1049615"},{"alt":"https://movie.douban.com/celebrity/1329473/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1486452827.09.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1486452827.09.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1486452827.09.jpg"},"name":"拉迪卡·艾普特","id":"1329473"},{"alt":"https://movie.douban.com/celebrity/1018143/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1402744446.01.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1402744446.01.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1402744446.01.jpg"},"name":"索娜姆·卡普尔","id":"1018143"}],"collect_count":23860,"original_title":"Padman","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1311506/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1373516277.14.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1373516277.14.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1373516277.14.jpg"},"name":"R·巴尔基","id":"1311506"}],"year":"2018","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2540940050.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2540940050.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2540940050.jpg"},"alt":"https://movie.douban.com/subject/27198855/","id":"27198855"},{"rating":{"max":10,"average":6.4,"stars":"35","min":0},"genres":["喜剧","动画"],"title":"绿毛怪格林奇","casts":[{"alt":"https://movie.douban.com/celebrity/1009405/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p41072.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p41072.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p41072.jpg"},"name":"本尼迪克特·康伯巴奇","id":"1009405"},{"alt":"https://movie.douban.com/celebrity/1387860/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1517222739.45.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1517222739.45.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1517222739.45.jpg"},"name":"卡梅伦·丝蕾","id":"1387860"},{"alt":"https://movie.douban.com/celebrity/1031815/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p32735.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p32735.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p32735.jpg"},"name":"拉什达·琼斯","id":"1031815"}],"collect_count":5582,"original_title":"The Grinch","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1280591/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1467266335.21.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1467266335.21.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1467266335.21.jpg"},"name":"亚罗·切尼","id":"1280591"},{"alt":"https://movie.douban.com/celebrity/1032291/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34173.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34173.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34173.jpg"},"name":"斯科特·摩西尔","id":"1032291"}],"year":"2018","images":{"small":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2539666559.jpg","large":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2539666559.jpg","medium":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2539666559.jpg"},"alt":"https://movie.douban.com/subject/23774869/","id":"23774869"},{"rating":{"max":10,"average":6.5,"stars":"35","min":0},"genres":["剧情","悬疑"],"title":"淡蓝琥珀","casts":[{"alt":"https://movie.douban.com/celebrity/1346550/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1505213224.8.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1505213224.8.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1505213224.8.jpg"},"name":"王真儿","id":"1346550"},{"alt":"https://movie.douban.com/celebrity/1274390/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33149.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33149.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33149.jpg"},"name":"吕聿来","id":"1274390"},{"alt":"https://movie.douban.com/celebrity/1274576/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1370503685.74.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1370503685.74.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1370503685.74.jpg"},"name":"耿乐","id":"1274576"}],"collect_count":2609,"original_title":"淡蓝琥珀","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1381116/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1527068725.8.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1527068725.8.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1527068725.8.jpg"},"name":"周劼","id":"1381116"}],"year":"2018","images":{"small":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2542212488.jpg","large":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2542212488.jpg","medium":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2542212488.jpg"},"alt":"https://movie.douban.com/subject/27069377/","id":"27069377"},{"rating":{"max":10,"average":8.5,"stars":"45","min":0},"genres":["剧情","犯罪","悬疑"],"title":"网络谜踪","casts":[{"alt":"https://movie.douban.com/celebrity/1019015/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1328.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1328.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1328.jpg"},"name":"约翰·赵","id":"1019015"},{"alt":"https://movie.douban.com/celebrity/1393518/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1534082266.94.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1534082266.94.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1534082266.94.jpg"},"name":"米切尔·拉","id":"1393518"},{"alt":"https://movie.douban.com/celebrity/1022653/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p24903.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p24903.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p24903.jpg"},"name":"黛博拉·梅辛","id":"1022653"}],"collect_count":203144,"original_title":"Searching","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1393516/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1543483760.83.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1543483760.83.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1543483760.83.jpg"},"name":"阿尼什·查甘蒂","id":"1393516"}],"year":"2018","images":{"small":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2542848758.jpg","large":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2542848758.jpg","medium":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2542848758.jpg"},"alt":"https://movie.douban.com/subject/27615441/","id":"27615441"},{"rating":{"max":10,"average":8.4,"stars":"45","min":0},"genres":["剧情","家庭"],"title":"狗十三","casts":[{"alt":"https://movie.douban.com/celebrity/1326897/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1521363568.57.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1521363568.57.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1521363568.57.jpg"},"name":"张雪迎","id":"1326897"},{"alt":"https://movie.douban.com/celebrity/1274914/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1511761726.07.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1511761726.07.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1511761726.07.jpg"},"name":"果靖霖","id":"1274914"},{"alt":"https://movie.douban.com/celebrity/1275239/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1543244622.61.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1543244622.61.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1543244622.61.jpg"},"name":"智一桐","id":"1275239"}],"collect_count":115342,"original_title":"狗十三","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1274278/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1513235776.32.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1513235776.32.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1513235776.32.jpg"},"name":"曹保平","id":"1274278"}],"year":"2013","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2540513831.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2540513831.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2540513831.jpg"},"alt":"https://movie.douban.com/subject/25716096/","id":"25716096"},{"rating":{"max":10,"average":8.2,"stars":"45","min":0},"genres":["剧情","喜剧"],"title":"无名之辈","casts":[{"alt":"https://movie.douban.com/celebrity/1274626/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1415455964.31.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1415455964.31.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1415455964.31.jpg"},"name":"陈建斌","id":"1274626"},{"alt":"https://movie.douban.com/celebrity/1362973/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1478066140.77.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1478066140.77.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1478066140.77.jpg"},"name":"任素汐","id":"1362973"},{"alt":"https://movie.douban.com/celebrity/1316365/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1541855083.14.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1541855083.14.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1541855083.14.jpg"},"name":"潘斌龙","id":"1316365"}],"collect_count":432137,"original_title":"无名之辈","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1326752/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1541992522.36.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1541992522.36.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1541992522.36.jpg"},"name":"饶晓志","id":"1326752"}],"year":"2018","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2539661066.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2539661066.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2539661066.jpg"},"alt":"https://movie.douban.com/subject/27110296/","id":"27110296"},{"rating":{"max":10,"average":0,"stars":"00","min":0},"genres":["剧情","悬疑","惊悚"],"title":"夜魔奇案","casts":[{"alt":"https://movie.douban.com/celebrity/1313627/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p19251.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p19251.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p19251.jpg"},"name":"乔乔","id":"1313627"},{"alt":"https://movie.douban.com/celebrity/1404777/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/pkDcFPn8vZoYcel_avatar_uploaded1542357251.82.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/pkDcFPn8vZoYcel_avatar_uploaded1542357251.82.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/pkDcFPn8vZoYcel_avatar_uploaded1542357251.82.jpg"},"name":"何若鹤","id":"1404777"},{"alt":"https://movie.douban.com/celebrity/1404776/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/pkDcFPn8vZoYcel_avatar_uploaded1542357182.07.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/pkDcFPn8vZoYcel_avatar_uploaded1542357182.07.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/pkDcFPn8vZoYcel_avatar_uploaded1542357182.07.jpg"},"name":"马江德","id":"1404776"}],"collect_count":22,"original_title":"夜魔奇案","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1404775/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1542707003.52.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1542707003.52.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1542707003.52.jpg"},"name":"邵大卫","id":"1404775"}],"year":"2018","images":{"small":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2543048189.jpg","large":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2543048189.jpg","medium":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2543048189.jpg"},"alt":"https://movie.douban.com/subject/30379943/","id":"30379943"},{"rating":{"max":10,"average":0,"stars":"00","min":0},"genres":["剧情","历史"],"title":"照相师","casts":[{"alt":"https://movie.douban.com/celebrity/1313180/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p15360.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p15360.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p15360.jpg"},"name":"谢钢","id":"1313180"},{"alt":"https://movie.douban.com/celebrity/1313315/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1544078646.12.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1544078646.12.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1544078646.12.jpg"},"name":"刘牧","id":"1313315"},{"alt":"https://movie.douban.com/celebrity/1346557/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1422792176.29.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1422792176.29.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1422792176.29.jpg"},"name":"康磊","id":"1346557"}],"collect_count":222,"original_title":"照相师","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1342506/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1409562550.22.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1409562550.22.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1409562550.22.jpg"},"name":"张唯","id":"1342506"}],"year":"2018","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2541659816.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2541659816.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2541659816.jpg"},"alt":"https://movie.douban.com/subject/30258232/","id":"30258232"},{"rating":{"max":10,"average":8.2,"stars":"45","min":0},"genres":["喜剧","动画","奇幻"],"title":"无敌破坏王2：大闹互联网","casts":[{"alt":"https://movie.douban.com/celebrity/1017907/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p12381.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p12381.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p12381.jpg"},"name":"约翰·C·赖利","id":"1017907"},{"alt":"https://movie.douban.com/celebrity/1036448/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1361976296.51.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1361976296.51.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1361976296.51.jpg"},"name":"萨拉·西尔弗曼","id":"1036448"},{"alt":"https://movie.douban.com/celebrity/1044996/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1373894437.28.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1373894437.28.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1373894437.28.jpg"},"name":"盖尔·加朵","id":"1044996"}],"collect_count":189629,"original_title":"Ralph Breaks the Internet","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1307788/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1465612829.87.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1465612829.87.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1465612829.87.jpg"},"name":"菲尔·约翰斯顿","id":"1307788"},{"alt":"https://movie.douban.com/celebrity/1324037/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1457505501.8.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1457505501.8.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1457505501.8.jpg"},"name":"瑞奇·摩尔","id":"1324037"}],"year":"2018","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2537667301.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2537667301.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2537667301.jpg"},"alt":"https://movie.douban.com/subject/20438964/","id":"20438964"},{"rating":{"max":10,"average":6.7,"stars":"35","min":0},"genres":["剧情"],"title":"冥王星时刻","casts":[{"alt":"https://movie.douban.com/celebrity/1274550/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1401519674.96.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1401519674.96.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1401519674.96.jpg"},"name":"王学兵","id":"1274550"},{"alt":"https://movie.douban.com/celebrity/1311659/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p20368.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p20368.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p20368.jpg"},"name":"刘丹","id":"1311659"},{"alt":"https://movie.douban.com/celebrity/1316471/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1526497889.53.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1526497889.53.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1526497889.53.jpg"},"name":"曾美慧孜","id":"1316471"}],"collect_count":4013,"original_title":"冥王星时刻","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1274499/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p37421.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p37421.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p37421.jpg"},"name":"章明","id":"1274499"}],"year":"2018","images":{"small":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2519560628.jpg","large":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2519560628.jpg","medium":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2519560628.jpg"},"alt":"https://movie.douban.com/subject/26887153/","id":"26887153"},{"rating":{"max":10,"average":0,"stars":"00","min":0},"genres":["剧情"],"title":"闽宁镇","casts":[{"alt":"https://movie.douban.com/celebrity/1316038/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1494832042.34.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1494832042.34.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1494832042.34.jpg"},"name":"姬他","id":"1316038"},{"alt":"https://movie.douban.com/celebrity/1274819/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p37725.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p37725.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p37725.jpg"},"name":"王洛勇","id":"1274819"},{"alt":"https://movie.douban.com/celebrity/1318471/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p43014.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p43014.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p43014.jpg"},"name":"曹馨月","id":"1318471"}],"collect_count":59,"original_title":"闽宁镇","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1318477/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1352183247.45.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1352183247.45.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1352183247.45.jpg"},"name":"董玲","id":"1318477"}],"year":"2018","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2541309693.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2541309693.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2541309693.jpg"},"alt":"https://movie.douban.com/subject/30391495/","id":"30391495"},{"rating":{"max":10,"average":6.6,"stars":"35","min":0},"genres":["喜剧","动作","冒险"],"title":"憨豆特工3","casts":[{"alt":"https://movie.douban.com/celebrity/1040996/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p884.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p884.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p884.jpg"},"name":"罗温·艾金森","id":"1040996"},{"alt":"https://movie.douban.com/celebrity/1115015/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1543510678.03.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1543510678.03.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1543510678.03.jpg"},"name":"本·米勒","id":"1115015"},{"alt":"https://movie.douban.com/celebrity/1035654/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p22061.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p22061.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p22061.jpg"},"name":"欧嘉·柯瑞兰寇","id":"1035654"}],"collect_count":47140,"original_title":"Johnny English Strikes Again","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1178991/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1393516861.67.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1393516861.67.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1393516861.67.jpg"},"name":"大卫·科尔","id":"1178991"}],"year":"2018","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2538352472.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2538352472.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2538352472.jpg"},"alt":"https://movie.douban.com/subject/27073234/","id":"27073234"},{"rating":{"max":10,"average":0,"stars":"00","min":0},"genres":["剧情","传记"],"title":"黄大年","casts":[{"alt":"https://movie.douban.com/celebrity/1318287/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1416383115.51.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1416383115.51.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1416383115.51.jpg"},"name":"张秋歌","id":"1318287"},{"alt":"https://movie.douban.com/celebrity/1011398/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p4153.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p4153.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p4153.jpg"},"name":"史可","id":"1011398"},{"alt":"https://movie.douban.com/celebrity/1313445/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33457.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33457.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p33457.jpg"},"name":"哈斯高娃","id":"1313445"}],"collect_count":39,"original_title":"黄大年","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1351710/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1474989094.39.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1474989094.39.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1474989094.39.jpg"},"name":"成科","id":"1351710"}],"year":"2018","images":{"small":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541830929.jpg","large":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541830929.jpg","medium":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541830929.jpg"},"alt":"https://movie.douban.com/subject/30185236/","id":"30185236"}]
     * title : 正在上映的电影-北京
     */

    private int count;
    private int start;
    private int total;
    private String title;
    private List<SubjectsBean> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public static class SubjectsBean {
        /**
         * rating : {"max":10,"average":0,"stars":"00","min":0}
         * genres : ["喜剧","奇幻"]
         * title : 天气预爆
         * casts : [{"alt":"https://movie.douban.com/celebrity/1274979/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg"},"name":"肖央","id":"1274979"},{"alt":"https://movie.douban.com/celebrity/1323516/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1368850348.93.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1368850348.93.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1368850348.93.jpg"},"name":"杜鹃","id":"1323516"},{"alt":"https://movie.douban.com/celebrity/1327084/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1363597076.12.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1363597076.12.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1363597076.12.jpg"},"name":"常远","id":"1327084"}]
         * collect_count : 1681
         * original_title : 天气预爆
         * subtype : movie
         * directors : [{"alt":"https://movie.douban.com/celebrity/1274979/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg"},"name":"肖央","id":"1274979"}]
         * year : 2018
         * images : {"small":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541281178.jpg","large":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541281178.jpg","medium":"https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541281178.jpg"}
         * alt : https://movie.douban.com/subject/26994789/
         * id : 26994789
         */

        private RatingBean rating;
        private String title;
        private int collect_count;
        private String original_title;
        private String subtype;
        private String year;
        private ImagesBean images;
        private String alt;
        private String id;
        private List<String> genres;
        private List<CastsBean> casts;
        private List<DirectorsBean> directors;

        public RatingBean getRating() {
            return rating;
        }

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public List<CastsBean> getCasts() {
            return casts;
        }

        public void setCasts(List<CastsBean> casts) {
            this.casts = casts;
        }

        public List<DirectorsBean> getDirectors() {
            return directors;
        }

        public void setDirectors(List<DirectorsBean> directors) {
            this.directors = directors;
        }

        public static class RatingBean {
            /**
             * max : 10
             * average : 0
             * stars : 00
             * min : 0
             */

            private int max;
            private int average;
            private String stars;
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getAverage() {
                return average;
            }

            public void setAverage(int average) {
                this.average = average;
            }

            public String getStars() {
                return stars;
            }

            public void setStars(String stars) {
                this.stars = stars;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        public static class ImagesBean {
            /**
             * small : https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541281178.jpg
             * large : https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541281178.jpg
             * medium : https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541281178.jpg
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }

        public static class CastsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1274979/
             * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg"}
             * name : 肖央
             * id : 1274979
             */

            private String alt;
            private AvatarsBean avatars;
            private String name;
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsBean getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsBean avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class AvatarsBean {
                /**
                 * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg
                 * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg
                 * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg
                 */

                private String small;
                private String large;
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }

        public static class DirectorsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1274979/
             * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg"}
             * name : 肖央
             * id : 1274979
             */

            private String alt;
            private AvatarsBeanX avatars;
            private String name;
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsBeanX getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsBeanX avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class AvatarsBeanX {
                /**
                 * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg
                 * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg
                 * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1518431956.11.jpg
                 */

                private String small;
                private String large;
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }
    }
}
