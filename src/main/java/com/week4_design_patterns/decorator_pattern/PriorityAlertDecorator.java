package com.week4_design_patterns.decorator_pattern;

public class PriorityAlertDecorator extends AlertDecorator {
    
    private String priorityLevel;

    public PriorityAlertDecorator(Alert alert, String priorityLevel) {
        super(alert, "Priority: " + priorityLevel);
        this.priorityLevel = priorityLevel;
    }

    @Override
    public String toString() {
        return super.toString() + ", Priority Level: " + priorityLevel;
    }
    
}