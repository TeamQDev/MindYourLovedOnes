package com.mindyourelders.MyHealthCareWishes.HomeActivity;

import com.mindyourelders.MyHealthCareWishes.model.PersonalInfo;

/**
 * Created by welcome on 11/1/2017.
 */

public class MessageString {



   /* public StringBuffer getProfile() {
        StringBuffer result = new StringBuffer();
        if (Individual.Message.size() > 0) {
            result.append("Mind Your Elders Overview");
            result.append("\n");
            result.append(Individual.Message.get(0));
            result.append("\n");
            for (int i = 1; i < Individual.Message.size(); i++) {
                result.append(Individual.Message.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }
        return result;
    }
*/
    public StringBuffer getProfileProfile() {
        StringBuffer result = new StringBuffer();
        if (Individual.messageInfo.size() > 0) {
            result.append(Individual.messageInfo.get(0));
            result.append("\n");
            for (int i = 1; i < Individual.messageInfo.size(); i++) {

                result.append(Individual.messageInfo.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;

    }

    public StringBuffer getProfileUser(PersonalInfo personalInfo) {
        StringBuffer result = new StringBuffer();
        if (Individual.messageInfo.size() > 0) {
            result.append(Individual.messageInfo.get(0));
            result.append("\n");
            for (int i = 1; i < Individual.messageInfo.size(); i++) {

                result.append(Individual.messageInfo.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getProfileUser() {

        StringBuffer result = new StringBuffer();
        if (Individual.messageInfo2.size() > 0) {
            result.append(Individual.messageInfo2.get(0));
            result.append("\n");
            for (int i = 1; i < Individual.messageInfo2.size(); i++) {

                result.append(Individual.messageInfo2.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

}
