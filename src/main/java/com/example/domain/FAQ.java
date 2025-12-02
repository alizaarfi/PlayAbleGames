package com.example.domain;

import io.micronaut.data.annotation.*;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@MappedEntity("faq")
public class FAQ {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    @MappedProperty("FaqID")
    private Long faqID;

    @MappedProperty("Question")
    private String question;

    @MappedProperty("Answer")
    private String answer;

    // Getters and Setters
    public Long getFaqID() { return faqID; }
    public void setFaqID(Long faqID) { this.faqID = faqID; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
}