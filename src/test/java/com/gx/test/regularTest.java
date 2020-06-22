package com.gx.test;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regularTest {
    public static void main(String[] args) {

    }

    @Test
    public void ytbLiveIdRegular(){
        //        String content = "https://www.youtube.com/watch?v=OrGW9XpqCrA&pbjreload=101";
//        String content = "http://www.youtube.com/watch?v=OrGW9XpqCrA&pbjreload=101";
//        String content = "www.youtube.com/watch?v=OrGW9XpqCrA&pbjreload=101";
//        String content = "youtube.com/watch?v=OrGW9XpqCrA&pbjreload=101";
//        String content = "youtube.cn/watch?v=OrGW9XpqCrA&pbjreload=101";
//        String content = "youtube.96+fk/watch?v=OrGW9XpqCrA&pbjreload=101";
//        String content = "youtube.96fk/watch?v=OrGW9XpqCrA&pbjreload=101";
//        String content = "https://www.youtube.com/watch?v=OrGW9XpqCrA";
//        String content = "https://www.youtube.com/watch?v=OrGW9XpqCrA&pbjreload=101";
//        String content = "https://www.youtube.com/watch?v=OrGW9XpqCrA&pbjreload=101/rr32r32";
        String content = "http://www.youtube.com/watch?v=OrGW9XpqCrA&pbjreload=101/rr32r32";
//        String pattern = "http(s)?://.*";
//        String pattern = "(http(s)?://)?.*";
//        String pattern = "(http(s)?://)?(www.)?.*";
//        String pattern = "(http(s)?://)?(www.)?youtube.(\\w)+/.*";
        String pattern = "(http(s)?://)?(www.)?youtube.(\\w)+/watch\\?v=.*";
        System.out.println(Pattern.matches(pattern, content));
    }

    @Test
    public void ytbUserIdRegular(){
//        String content = "https://www.youtube.com/channel/UCrkzfc2yf-7q6pd7EtzgNaQ";
//        String content = "https://www.youtube.com/channel/UCrkzfc2yf-7q6pd7EtzgNaQ/videos";
//        String content = "youtube.com/channel/UCrkzfc2yf-7q6pd7EtzgNaQ/videos";
//        String content = "youtube.com/fewfe/channel/UCrkzfc2yf-7q6pd7EtzgNaQ/videos";
        String content = "https://www.youtube.com/user/MTV2/23432";
//        String pattern = "(http(s)?://)?(www.)?youtube.(\\w)+/channel/.*";
        String pattern = "(http(s)?://)?(www.)?youtube.(\\w)+/user/.*";
        System.out.println(Pattern.matches(pattern, content));
    }

    @Test
    public void fbLiveIdRegular(){
//        String content = "https://www.facebook.com/watch/live/?v={liveid}&external_log_id=ec560093be69507a925af04fccc87f0e";
//        String content = "https://www.facebook.com/soyelpayasodelatoledo/videos/{liveid}/";
        String content = "https://www.facebook.com/SRI.SATHYA.SAI.BABA/videos/{liveid}/UzpfSTEwMDA1MDkyMjgzMjcyNToxMzMyNDE2NDgzODMyNjI/?id=100050922832725";
//        String pattern = "(http(s)?://)?(www.)?facebook.(\\w)+/watch/live/\\?v=.*";
        String pattern = "(http(s)?://)?(www.)?facebook.(\\w)+/.*/videos/.*";
        System.out.println(Pattern.matches(pattern, content));
    }

    @Test
    public void fbUserIdRegular(){
//        String content = "https://www.facebook.com/profile.php?id=100052339394229";
        String content = "https://www.facebook.com/${user_id}";
//        String pattern = "(http(s)?://)?(www.)?facebook.(\\w)+/profile.php\\?id=.*";
        String pattern = "(http(s)?://)?(www.)?facebook.(\\w)+/.*";
        System.out.println(Pattern.matches(pattern, content));
    }

    @Test
    public void strint01(){
        StringBuffer s1 = new StringBuffer("432432432432");
        string02(s1);
        System.out.println(s1);
    }

    public void string02(StringBuffer s1){
        s1.append("rr32r32r2");
    }
}
