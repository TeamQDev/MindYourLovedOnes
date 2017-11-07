package com.mindyourelders.MyHealthCareWishes.pdfCreation;

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

    public StringBuffer getMedicalInfo() {StringBuffer result = new StringBuffer();
        if (Individual.messageInfo3.size() > 0) {
            result.append(Individual.messageInfo3.get(0));
            result.append("\n");
            for (int i = 1; i < Individual.messageInfo3.size(); i++) {

                result.append(Individual.messageInfo3.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;

    }

    public StringBuffer getEmergencyInfo() {
        StringBuffer result = new StringBuffer();
        if (Individual.messageEmergency.size() > 0) {
            result.append(Individual.messageEmergency.get(0));
            result.append("\n");
            for (int i = 1; i < Individual.messageEmergency.size(); i++) {

                result.append(Individual.messageEmergency.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getPhysicianInfo() {
        StringBuffer result = new StringBuffer();
        if (Individual.messagePhysician.size() > 0) {
            result.append(Individual.messagePhysician.get(0));
            result.append("\n");
            for (int i = 1; i < Individual.messagePhysician.size(); i++) {

                result.append(Individual.messagePhysician.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getProxyInfo() {
        StringBuffer result = new StringBuffer();
        if (Individual.messageProxy.size() > 0) {
            result.append(Individual.messageProxy.get(0));
            result.append("\n");
            for (int i = 1; i < Individual.messageProxy.size(); i++) {

                result.append(Individual.messageProxy.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getDoctorsInfo() {
        StringBuffer result = new StringBuffer();
        if (Specialty.messageDoctor.size() > 0) {
            result.append(Specialty.messageDoctor.get(0));
            result.append("\n");
            for (int i = 1; i < Specialty.messageDoctor.size(); i++) {

                result.append(Specialty.messageDoctor.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getHospitalInfo() {
        StringBuffer result = new StringBuffer();
        if (Specialty.messageHospital.size() > 0) {
            result.append(Specialty.messageHospital.get(0));
            result.append("\n");
            for (int i = 1; i < Specialty.messageHospital.size(); i++) {

                result.append(Specialty.messageHospital.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getPharmacyInfo() {
        StringBuffer result = new StringBuffer();
        if (Specialty.messagePharmacy.size() > 0) {
            result.append(Specialty.messagePharmacy.get(0));
            result.append("\n");
            for (int i = 1; i < Specialty.messagePharmacy.size(); i++) {

                result.append(Specialty.messagePharmacy.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getAideInfo() {
        StringBuffer result = new StringBuffer();
        if (Specialty.messageAides.size() > 0) {
            result.append(Specialty.messageAides.get(0));
            result.append("\n");
            for (int i = 1; i < Specialty.messageAides.size(); i++) {

                result.append(Specialty.messageAides.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getFinanceInfo() {
        StringBuffer result = new StringBuffer();
        if (Specialty.messageFinance.size() > 0) {
            result.append(Specialty.messageFinance.get(0));
            result.append("\n");
            for (int i = 1; i < Specialty.messageFinance.size(); i++) {

                result.append(Specialty.messageFinance.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getInsuranceInfo() {
        StringBuffer result = new StringBuffer();
        if (InsurancePdf.messageInsurance.size() > 0) {
            result.append(InsurancePdf.messageInsurance.get(0));
            result.append("\n");
            for (int i = 1; i < InsurancePdf.messageInsurance.size(); i++) {

                result.append(InsurancePdf.messageInsurance.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }

    public StringBuffer getAppointInfo() {
        StringBuffer result = new StringBuffer();
        if (EventPdf.messageAppoint.size() > 0) {
            result.append(EventPdf.messageAppoint.get(0));
            result.append("\n");
            for (int i = 1; i < EventPdf.messageAppoint.size(); i++) {

                result.append(EventPdf.messageAppoint.get(i));
                if (i % 2 == 0 && i >= 2) {
                    result.append("\n");

                }

            }
        }

        return result;
    }
}
