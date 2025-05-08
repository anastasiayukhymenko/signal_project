package com.week4_design_patterns.decorator_pattern;

public class AlertDecorator implements Alert {

    private Alert alert;
    private String additionalInfo;

    public AlertDecorator(Alert alert, String additionalInfo) {
        this.alert = alert;
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String getPatientId() {
        return alert.getPatientId();
    }

    @Override
    public String getCondition() {
        return alert.getCondition() + " - " + additionalInfo;
    }

    @Override
    public long getTimestamp() {
        return alert.getTimestamp();
    }

    @Override
    public String toString() {
        return "Alert{" +
                "patientId='" + getPatientId() + '\'' +
                ", condition='" + getCondition() + '\'' +
                ", timestamp=" + getTimestamp() +
                '}';
    }
}
