package com.tsutsuku.artcollection.im.parse;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.im.Constant;
import com.tsutsuku.artcollection.utils.SharedPref;

/**
 * @Author Sun Renwei
 * @Create 2016/8/26
 * @Description Content
 */
public class UserProfileManager {
    private EaseUser currentUser;

    public synchronized EaseUser getCurrentUserInfo() {
        if (currentUser == null) {
            String username = EMClient.getInstance().getCurrentUser();
            currentUser = new EaseUser(username);
            currentUser.setAvatar(SharedPref.getString(Constants.AVATAR));
            currentUser.setNick(SharedPref.getString(Constants.NICK));
        }
        return currentUser;
    }

    public void setCurrentUserAvatar(String avatar) {
        getCurrentUserInfo().setAvatar(avatar);
        PreferenceManager.getInstance().setCurrentUserAvatar(avatar);
    }

    private String getCurrentUserAvatar() {
        return PreferenceManager.getInstance().getCurrentUserAvatar();
    }

    public void setCurrentUserNick(String nick) {
        getCurrentUserInfo().setNick(nick);
        PreferenceManager.getInstance().setCurrentUserNick(nick);
    }

    private String getCurrentUserNick() {
        return PreferenceManager.getInstance().getCurrentUserNick();
    }
}
