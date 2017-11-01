package com.mindyourelders.MyHealthCareWishes.HomeActivity;

/**
 * Created by welcome on 11/1/2017.
 */

class MessageString {
    public StringBuffer getProfile() {
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
}
