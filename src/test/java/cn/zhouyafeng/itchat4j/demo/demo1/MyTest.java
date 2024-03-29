package cn.zhouyafeng.itchat4j.demo.demo1;

import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.utils.DateUtil;
import cn.zhouyafeng.itchat4j.utils.RedisUtil;

import java.util.*;

/**
 *
 * @author https://github.com/yaphone
 * @date 创建时间：2017年4月28日 上午12:44:10
 * @version 1.0
 *
 */
public class MyTest {
	public static void main(String[] args) {
		String redisHS ="wx:hs:";
		String redisTQ ="wx:tq:";
		String redisXH ="wx:xh:";
		String qrPath = "F://itchat4j//login"; // 保存登陆二维码图片的路径，这里需要在本地新建目录
		IMsgHandlerFace msgHandler = new SimpleDemo(); // 实现IMsgHandlerFace接口的类
		Wechat wechat = new Wechat(msgHandler, qrPath); // 【注入】
		wechat.start(); // 启动服务，会在qrPath下生成一张二维码图片，扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片

		Calendar calendar = Calendar.getInstance();
		Date firstTime = calendar.getTime();
		// 间隔：2分钟
		long period = 1000 * 60 * 60;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("执行了...");
				String hour = DateUtil.getHour();
				String min =DateUtil.getTime();
				System.out.println("现在的小时:"+hour);

				/*Set<String> stringSet = RedisUtil.keys(redisHS+hour+"*");
				for (String key:stringSet) {
					System.out.println(key) ;
					System.out.println(RedisUtil.get(key)) ;
					String toUser = key.replaceAll(redisHS+hour+":","");
					System.out.println(toUser) ;
					MessageTools.sendMsgById(RedisUtil.get(key),toUser);
				}*/
				MessageTools.sendMsgByRealName("喝水小助手提醒：现在"+hour+"点"+min+"分,和我一起成为一天八杯水的人吧！！!","夏康");
				MessageTools.sendMsgByRealName("喝水小助手提醒：现在"+hour+"点"+min+"分,和我一起成为一天八杯水的人吧！！!","杨颖");
				MessageTools.sendMsgByRealName("喝水小助手提醒：现在"+hour+"点"+min+"分,和我一起成为一天八杯水的人吧！！!","兰文娇");
			//	MessageTools.sendMsg(userId,text)
			}
		}, firstTime, period);
	}
}
