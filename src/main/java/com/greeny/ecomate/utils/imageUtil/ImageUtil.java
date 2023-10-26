package com.greeny.ecomate.utils.imageUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class ImageUtil {

    public static final String boardImageDirectory = "board";

    public static final String profileImageDirectory = "profile";

    public static final String challengeImageDirectory = "challenge";

    public static final String storeImageDirectory = "store";

    private static final String s3Url = "https://ecomate-s3.s3.ap-northeast-2.amazonaws.com";

    public static String getProfileImage(String imageName) {
        return getImage(profileImageDirectory, imageName);
    }

    public static String getBoardImage(String imageName) {
        return getImage(boardImageDirectory, imageName);
    }

    public static String getChallengeImage(String imageName) {
        return getImage(challengeImageDirectory, imageName);
    }

    public static String getStoreImage(String imageName) { return getImage(storeImageDirectory, imageName); }

    private static String getImage(String directory, String imageName) {
        if (imageName == null) {
            return null;
        }
        return s3Url + "/" + directory + "/" + imageName;
    }

}
