package cn.zhouyafeng.itchat4j.beans;

import com.alibaba.fastjson.JSONObject;

public class JqrInfo {


    public static JSONObject getJqrRequset(String text,String city,String userId){
        JSONObject jsonObject = new JSONObject();
        JSONObject userInfo = new JSONObject();
        userInfo.put("apiKey","4c4a331d564a4be0a4b1bfe072445d54");
        userInfo.put("userId",userId);
        JSONObject perception = new JSONObject();
        JSONObject selfInfo = new JSONObject();
        JSONObject location = new JSONObject();
        selfInfo.put("location",location);
        location.put("city",city);
        JSONObject inputText = new JSONObject();
        inputText.put("text",text);
        perception.put("inputText",inputText);
        perception.put("selfInfo",selfInfo);
        jsonObject.put("reqType",0);
        jsonObject.put("userInfo",userInfo);
        jsonObject.put("perception",perception);
        System.out.println(jsonObject.toString());
    return jsonObject;
    }
}
