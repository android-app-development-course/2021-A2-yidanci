package com.iwxyi.letsremember.Globals;

import com.iwxyi.letsremember.Utils.ConnectUtil;
import com.iwxyi.letsremember.Utils.StringCallback;
import com.iwxyi.letsremember.Utils.StringUtil;

public class User {
    public static String    user_id;    // 用户ID
    public static String username;   // 账号
    public static String password;   // 密码
    public static String nickname;   // 昵称
    public static String mobile;     // 手机号
    public static String email;      // 邮箱

    public static int integral;      // 积分
    public static int reciteShort;   // 背诵单词类
    public static int reciteMiddle;  // 背诵诗文类
    public static int reciteLong;    // 背诵长篇类
    public static int typeinCount;   // 录入

    public static int boxIceCount;    // 铁盒子数量
    public static int boxWoodCount;   // 银盒子数量
    public static int boxCopperCount; // 金盒子数量

    static {

    }

    public static boolean isLogin() {
        return user_id!=null&&user_id != "";
    }

    public static String id() {
        return ""+user_id;
    }

    public static String getName() {

        if (!isLogin())
            return "未登录";
        if (User.nickname!=null&&!User.nickname.isEmpty())
            return User.nickname;
        if (User.username!=null&&!User.username.isEmpty())
            return User.username;
        return "==您==";
    }

    public static int getRecite() {
        return reciteShort+reciteMiddle+reciteLong;
    }

    /**
     * 新背诵了一篇
     */
    public static void addRecite(String str) {
        int eng = 0, chi = 0;
        for (int i = 0; i < str .length()-1; i++) {
            String c = str.substring(i, i+1);
            if (StringUtil.canMatch(c, "\\d"))
                eng++;
            if (StringUtil.canMatch(c, "[^x00-xff]"))
                chi++;
        }
        if (eng == 0 && str.length() > 40 && chi > str.length() * 0.8) {
            reciteMiddle++;
            App.setVal("reciteMiddle", reciteMiddle);
            String[] params = {"user_id", ""+user_id, "recite_middle", ""+reciteMiddle};
            ConnectUtil.Post(Paths.getNetPath("updateUserInfo"), params);
            addIntegral(1);
        } else if (str.length() > 200) {
            reciteLong++;
            App.setVal("reciteLong", reciteLong);
            String[] params = {"user_id", ""+user_id, "recite_long", ""+reciteLong};
            ConnectUtil.Post(Paths.getNetPath("updateUserInfo"), params);
            addIntegral(3);
        } else {
            reciteShort++;
            App.setVal("reciteShort", reciteShort);
            String[] params = {"user_id", ""+user_id, "recite_short", ""+reciteShort};
            ConnectUtil.Post(Paths.getNetPath("updateUserInfo"), params);
            addIntegral(10);
        }
    }

    public static void addTypeinCount() {
        typeinCount++;
        App.setVal("typeinCount", typeinCount);
    }

    public static void addIntegral(int x) {
        integral += x;
        App.setVal("integral", integral);

        String[] params = {"user_id", ""+user_id, "integral", ""+integral};
        ConnectUtil.Post(Paths.getNetPath("updateUserInfo"), params);
    }


}
