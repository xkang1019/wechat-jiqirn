package cn.zhouyafeng.itchat4j.searchApi;

import cn.zhouyafeng.itchat4j.beans.JqrInfo;
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

    public static final String URL_XH = "http://api.jisuapi.com/xiaohua/text";
    public static final int pagenum = 1;
    public static final int pagesize = 1;
    public static final String sort = "rand";//addtime/rand


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

    public static  String GetXH() {
        String result = null;
        String url = URL_XH + "?pagenum=" + pagenum + "&pagesize=" + pagesize + "&sort=" + sort + "&appkey=" + APPKEY;
        StringBuffer contentAll = new StringBuffer();
        contentAll.append("喝水冷笑话-->\n\n");
        try {
            result = HttpUtils.sendGet(url, "utf-8");
            JSONObject json = JSONObject.parseObject(result);
            if (json.getInteger("status") != 0) {
                return "***";
            } else {
                JSONObject result_1 = json.getJSONObject("result");
               /* String total = resultarr.getString("total");
                String pagenum = resultarr.getString("pagenum");*/
                String pagesize = result_1.getString("pagesize");
               // System.out.println(total + " " + pagenum + " " + pagesize);
                JSONArray resultarr = result_1.getJSONArray("list");
                if (resultarr != null) {
                    for (int i = 0; i < resultarr.size(); i++) {
                        JSONObject obj =  resultarr.getJSONObject(i);
                        String content = obj.getString("content");
                        contentAll.append(content+"\n");
                    }
                }
                return contentAll.toString();
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "***";
        }
    }


    public static  String WXJQR(String text,String city,String userId) {
        StringBuffer contentAll = new StringBuffer();
        String result =   HttpUtils.postJson("http://openapi.tuling123.com/openapi/api/v2", JqrInfo.getJqrRequset(text,city,userId)).toJSONString();
        JSONObject json = JSONObject.parseObject(result);
       try {
        JSONArray result_1 = json.getJSONArray("results");
        if (result_1 != null) {
            for (int i = 0; i < result_1.size(); i++) {
                JSONObject obj =  result_1.getJSONObject(i);
                if ("url".equals(obj.getString("resultType"))) {
                    JSONObject values = obj.getJSONObject("values");
                    contentAll.append(values.getString("url") + "\n");
                }else if ("text".equals(obj.getString("resultType"))) {
                    JSONObject values = obj.getJSONObject("values");
                    contentAll.append(values.getString("text")+ "\n");
                }
            }
        }
           System.out.println(result);
           return contentAll.toString();
       }catch (Exception e){
           return "喝水菌脑袋都是水...";
       }

    }
    public static void main(String[] args) throws Exception {
        System.out.println(WXJQR("我想喝水","深圳","1")) ;

        System.out.println(JqrInfo.getJqrRequset("我想喝水","深圳","1"));
    }
}
