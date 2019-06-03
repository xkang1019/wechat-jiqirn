package cn.zhouyafeng.itchat4j.searchApi;

import cn.zhouyafeng.itchat4j.utils.HttpUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;



public class SerchApi {

        public static final String APPKEY = "3bc453b3c23e0218";// 你的appkey
        //周公解梦
        public static final String URL = "http://api.jisuapi.com/dream/search";
        //public static final String keyword = "鞋";//utf-8

        public static String GetZGJM(String keyword)  {
            String result = null;
            String url = null;
            try {
                url = URL + "?appkey=" + APPKEY + "&keyword=" + URLEncoder.encode(keyword,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            StringBuffer contentAll = new StringBuffer();
            try {
                result = HttpUtils.sendGet(url, "utf-8");
                JSONObject json = JSONObject.parseObject(result);
                if (json.getInteger("status") != 0) {
                  //  System.out.println(json.getString("msg"));
                    return "解梦菌困了";
                } else {
                    JSONArray resultarr = json.getJSONArray("result");
                    for (int i = 0; i < resultarr.size(); i++) {
                        JSONObject obj =  resultarr.getJSONObject(i);
                        String name = obj.getString("name");
                        String content = obj.getString("content");
                        contentAll.append(name + " " + content);
                    }
                    return  contentAll.toString().replaceAll("<p>","\n").replaceAll("</p>","\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "解梦菌困了";
            }
        }


    public static void main(String[] args) throws Exception {
        System.out.println(GetZGJM("足球")) ;
    }
}
