package com.app.b4s.model;

public class Answered {
    String qid;

    public Answered(String qid, String isAnswered) {
        this.isAnswered=isAnswered;
        this.qid=qid;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(String isAnswered) {
        this.isAnswered = isAnswered;
    }

    String isAnswered;
}
