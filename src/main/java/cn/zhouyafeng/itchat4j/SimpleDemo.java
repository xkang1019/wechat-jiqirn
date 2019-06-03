package cn.zhouyafeng.itchat4j;

import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.beans.RecommendInfo;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.searchApi.SerchApi;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;
import cn.zhouyafeng.itchat4j.utils.tools.DownloadTools;
import org.apache.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 简单示例程序，收到文本信息自动回复原信息，收到图片、语音、小视频后根据路径自动保存
 * 
 * @author https://github.com/yaphone
 * @date 创建时间：2017年4月25日 上午12:18:09
 * @version 1.0
 *
 */
public class SimpleDemo implements IMsgHandlerFace {
	Logger LOG = Logger.getLogger(SimpleDemo.class);
	static String redisHS ="wx:hs";
	static String redisTQ ="wx:tq";
	static String redisXH ="wx:xh";
	@Override
	public String textMsgHandle(BaseMsg msg) {
		// String docFilePath = "D:/itchat4j/pic/1.jpg"; // 这里是需要发送的文件的路径
		if (!msg.isGroupMsg()) { // 群消息不处理
			// String userId = msg.getString("FromUserName");
			// MessageTools.sendFileMsgByUserId(userId, docFilePath); // 发送文件
			// MessageTools.sendPicMsgByUserId(userId, docFilePath);
			String text = msg.getText(); // 发送文本消息，也可调用MessageTools.sendFileMsgByUserId(userId,text);
			LOG.info(text);
			if (text.equals("111")) {
				WechatTools.logout();
			}
			if (text.equals("222")) {
				WechatTools.remarkNameByNickName("yaphone", "Hello");
			}
			if (text.equals("333")) { // 测试群列表
				System.out.print(WechatTools.getGroupNickNameList());
				System.out.print(WechatTools.getGroupIdList());
				System.out.print(Core.getInstance().getGroupMemeberMap());
			}
			if (text.equals("我要喝水")) { // 测试群列表
				//WechatTools.remarkNameByNickName(msg., "Hello");
				/*for (int i =10;i<18;i++) {
					RedisUtil.set(redisHS+":"+i+":"+msg.getFromUserName(),"我是喝水小助手,记得喝水!");
				}
				RedisUtil.set(redisHS+":"+22+":"+msg.getFromUserName(),"我是喝水小助手,记得喝水!");
				RedisUtil.set(redisHS+":"+23+":"+msg.getFromUserName(),"我是喝水小助手,记得喝水!");*/
				//RedisUtil.set(redis+msg.getFromUserName(),"我是喝水小助手,记得喝水!");
				/*WechatTools.remarkNameByNickName("kang<span class=\"emoji emoji1f38f\"></span>", "沙B");
				System.out.println(WechatTools.getUserNameByNickName("沙B"));*/
			/*	System.out.println(WechatTools.getUserNameByNickName("沙B"));
				System.out.println(msg.toString());*/
				MessageTools.sendMsgByRealName("我是喝水小助手,记得喝水!","夏康");
			}
			if (text.indexOf("喝水菌")!=-1) {
				String info = "1.周公解梦\n\n" +
						"  回复--> 解梦菌:关键字\n\n" +
						"2.喝水菌喝水去了...";

				return info;
			}
            if (text.indexOf("解梦菌")!=-1) {
				 String text_1 = text.replaceAll("解梦菌","").replaceAll("：","").replaceAll(":","");
					return SerchApi.GetZGJM(text_1);
            }
			return text;
		}
		return null;
	}

	@Override
	public String picMsgHandle(BaseMsg msg) {
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());// 这里使用收到图片的时间作为文件名
		String picPath = "/home/wechat/pic" + File.separator + fileName + ".jpg"; // 调用此方法来保存图片
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.PIC.getType(), picPath); // 保存图片的路径
		return "图片保存成功";
	}

	@Override
	public String voiceMsgHandle(BaseMsg msg) {
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String voicePath = "/home/wechat/voice" + File.separator + fileName + ".mp3";
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.VOICE.getType(), voicePath);
		return "声音保存成功";
	}

	@Override
	public String viedoMsgHandle(BaseMsg msg) {
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String viedoPath = "/home/wechat/viedo" + File.separator + fileName + ".mp4";
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.VIEDO.getType(), viedoPath);
		return "视频保存成功";
	}

	@Override
	public String nameCardMsgHandle(BaseMsg msg) {
		return "收到名片消息";
	}

	@Override
	public void sysMsgHandle(BaseMsg msg) { // 收到系统消息
		String text = msg.getContent();
		LOG.info(text);
	}

	@Override
	public String verifyAddFriendMsgHandle(BaseMsg msg) {
		MessageTools.addFriend(msg, true); // 同意好友请求，false为不接受好友请求
		RecommendInfo recommendInfo = msg.getRecommendInfo();
		String nickName = recommendInfo.getNickName();
		String province = recommendInfo.getProvince();
		String city = recommendInfo.getCity();
		String text = "你好，来自" + province + city + "的" + nickName + "， 欢迎添加我为好友！";
		return text;
	}

	@Override
	public String mediaMsgHandle(BaseMsg msg) {
		String fileName = msg.getFileName();
		String filePath = "/home/wechat/file" + File.separator + fileName; // 这里是需要保存收到的文件路径，文件可以是任何格式如PDF，WORD，EXCEL等。
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.MEDIA.getType(), filePath);
		return "文件" + fileName + "保存成功";
	}

}
