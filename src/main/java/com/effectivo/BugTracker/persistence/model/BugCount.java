package com.effectivo.BugTracker.persistence.model;

public class BugCount {

    Integer bugCount;

    public BugCount(Integer bugCount) {
        this.bugCount = bugCount;
    }

    public Integer getBugCount() {
        return bugCount;
    }

    public void setBugCount(Integer bugCount) {
        this.bugCount = bugCount;
    }
}
