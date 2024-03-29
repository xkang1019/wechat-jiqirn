package cn.zhouyafeng.itchat4j;


import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.searchApi.SerchApi;
import cn.zhouyafeng.itchat4j.utils.DateUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication
public class WechatApplication
{


    public static void main(String[] args)
    {
        String redisHS ="wx:hs:";
        String redisTQ ="wx:tq:";
        String redisXH ="wx:xh:";
        final int a = 0;
        //  String qrPath = "F://itchat4j//login"; // 保存登陆二维码图片的路径，这里需要在本地新建目录
        String qrPath = "/home/wechat"; // 保存登陆二维码图片的路径，这里需要在本地新建目录
        IMsgHandlerFace msgHandler = new SimpleDemo(); // 实现IMsgHandlerFace接口的类
        Wechat wechat = new Wechat(msgHandler, qrPath); // 【注入】
        wechat.start(); // 启动服务，会在qrPath下生成一张二维码图片，扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片

        Calendar calendar = Calendar.getInstance();
        Date firstTime = calendar.getTime();
        // 间隔：2分钟
        long period = 1000 * 60 * 45;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int limit  = 0;
            @Override
            public void run() {

                System.out.println("执行了...");
                int month = Integer.parseInt(DateUtil.getMonth());
                int day = Integer.parseInt(DateUtil.getDay());
                String hour = DateUtil.getHour();
                String min =DateUtil.getTime();
                System.out.println("现在的小时:"+hour);
                System.out.println("现在的小时:"+month);
                System.out.println("现在的小时:"+day);
				/*Set<String> stringSet = RedisUtil.keys(redisHS+hour+"*");
				for (String key:stringSet) {
					System.out.println(key) ;
					System.out.println(RedisUtil.get(key)) ;
					String toUser = key.replaceAll(redisHS+hour+":","");
					System.out.println(toUser) ;
					MessageTools.sendMsgById(RedisUtil.get(key),toUser);
				}*/if (Integer.parseInt(hour)>8&&Integer.parseInt(hour)<20&&!"12".equals(hour)&&!"13".equals(hour)&&!"14".equals(hour)){

                      String leng = SerchApi.GetJD();
                      leng= leng+"\n";
                      leng= leng+"\n历史今日-->\n\n";
                      leng = leng + SerchApi.GetJT(month+"",day+"",limit);
                      MessageTools.sendMsgByRealName("喝水菌提醒：和我一起成为一天八杯水的人吧." + leng, "夏康");
                      MessageTools.sendMsgByRealName("喝水菌提醒：和我一起成为一天八杯水的人吧." + leng, "杨颖");
                      MessageTools.sendMsgByRealName("喝水菌提醒：和我一起成为一天八杯水的人吧." + leng, "兰文娇");
                      MessageTools.sendMsgByRealName("喝水菌提醒：和我一起成为一天八杯水的人吧." + leng, "刘芳新");
                      MessageTools.sendMsgByRealName("喝水菌提醒：和我一起成为一天八杯水的人吧." + leng, "金梦");
                      MessageTools.sendMsgByRealName("喝水菌提醒：和我一起成为一天八杯水的人吧." + leng, "杨颖朋友");
                      limit++;
                }
              //	MessageTools.sendMsg(userId,text)
            }
        }, firstTime, period);
        SpringApplication.run(WechatApplication.class, args);

    }
}