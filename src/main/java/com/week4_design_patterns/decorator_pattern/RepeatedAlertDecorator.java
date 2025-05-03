package com.week4_design_patterns.decorator_pattern;

public class RepeatedAlertDecorator extends AlertDecorator {
    private int repeatCount;

    public RepeatedAlertDecorator(Alert alert, int repeatCount) {
        super(alert, "Repeated " + repeatCount + " times");
        this.repeatCount = repeatCount;
    }

    @Override
    public String toString() {
        return super.toString() + ", Repeat Count: " + repeatCount;
    
    }
}
