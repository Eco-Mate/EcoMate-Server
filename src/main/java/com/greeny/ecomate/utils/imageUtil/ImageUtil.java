package com.greeny.ecomate.utils.imageUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageUtil {

    private static final String boardImageDirectory = "board";

    private static final String profileImageDirectory = "profile";

    private static final String challengeImageDirectory = "challenge";

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

    private static String getImage(String directory, String imageName) {
        if (imageName == null) {
            return null;
        }
        return s3Url + "/" + directory + "/" + imageName;
    }

}
