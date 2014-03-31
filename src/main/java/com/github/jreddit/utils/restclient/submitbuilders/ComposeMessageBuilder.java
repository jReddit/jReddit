package com.github.jreddit.utils.restclient.submitbuilders;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ComposeMessageBuilder {

    private String captchaSolution;
    private String captchaIden;
    private String recipient;
    private String subject;
    private String messageBody;
    private String modhash;

    public static ComposeMessageBuilder composeMessage() {
        return new ComposeMessageBuilder();
    }

    public ComposeMessageBuilder withCaptchaSolution(String captchaSolution) {
        this.captchaSolution = captchaSolution;
        return this;
    }

    public ComposeMessageBuilder withCaptchaIden(String captchaIden) {
        this.captchaIden = captchaIden;
        return this;
    }

    public ComposeMessageBuilder withRecipient(String recipient) {
        this.recipient = recipient;
        return this;
    }

    public ComposeMessageBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public ComposeMessageBuilder withMessageBody(String messageBody) {
        this.messageBody = messageBody;
        return this;
    }

    public ComposeMessageBuilder withModhash(String modhash) {
        this.modhash = modhash;
        return this;
    }

    public List<NameValuePair> build() {
        if (recipient == null || recipient.isEmpty()) {
            throw new IllegalArgumentException("You cannot compose a message without a recipient specified");
        }

        if (subject == null || subject.isEmpty()) {
            throw new IllegalArgumentException("You cannot compose a message without a subject specified");
        }

        if (subject.length() > 100) {
            throw new IllegalArgumentException("You cannot compose a message with a subject greater than 100 characters specified");
        }

        if (messageBody == null || messageBody.isEmpty()) {
            throw new IllegalArgumentException("You cannot compose a message without a messageBody specified");
        }

        if (modhash == null || modhash.isEmpty()) {
            throw new IllegalArgumentException("You cannot compose a message without a modhash specified");
        }

        if ((captchaIden == null || captchaIden.isEmpty()) ^
                (captchaSolution == null || captchaSolution.isEmpty())) {
            throw new IllegalArgumentException("You must submit the captchaIden AND captchaSolution OR neither");
        }

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("to", recipient));
        postParams.add(new BasicNameValuePair("subject", subject));
        postParams.add(new BasicNameValuePair("text", messageBody));
        postParams.add(new BasicNameValuePair("uh", modhash));

        return postParams;
    }

}
