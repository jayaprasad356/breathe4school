package com.app.b4s.model;

public class Attach {
    String qid;
    String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    String explanation;


    public Attach(String qid, String attach_link, String answer, String explanation) {
        this.qid=qid;
        this.attach_link=attach_link;
        this.answer=answer;
        this.explanation=explanation;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getAttach_link() {
        return attach_link;
    }

    public void setAttach_link(String attach_link) {
        this.attach_link = attach_link;
    }

    String attach_link;
}
