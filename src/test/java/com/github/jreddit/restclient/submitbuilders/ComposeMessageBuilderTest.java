package com.github.jreddit.restclient.submitbuilders;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static com.github.jreddit.restclient.submitbuilders.ComposeMessageBuilder.composeMessage;

public class ComposeMessageBuilderTest {

    public static final String MESSAGE_BODY = "messageBody";
    public static final String MODHASH = "modhash";
    public static final String RECIPIENT = "recipient";
    public static final String SUBJECT = "subject";
    public static final String IDEN = "iden";
    public static final String SOLUTION = "solution";
    private ComposeMessageBuilder underTest;

    @Before
    public void setUp() {
        underTest = composeMessage();
    }

    @Test
    public void workingWithBasicRequirements() {
        underTest.withMessageBody(MESSAGE_BODY)
                .withModhash(MODHASH)
                .withRecipient(RECIPIENT)
                .withSubject(SUBJECT);

        underTest.build();
    }

    @Test
    public void workingWithAllFields() {
        underTest.withMessageBody(MESSAGE_BODY)
                .withModhash(MODHASH)
                .withRecipient(RECIPIENT)
                .withSubject(SUBJECT)
                .withCaptchaIden(IDEN)
                .withCaptchaSolution(SOLUTION);

        underTest.build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionWhenIdenButNoSolution() {
        underTest.withMessageBody(MESSAGE_BODY)
                .withModhash(MODHASH)
                .withRecipient(RECIPIENT)
                .withSubject(SUBJECT)
                .withCaptchaIden(IDEN);

        underTest.build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionWhenSolutionButNoIden() {
        underTest.withMessageBody(MESSAGE_BODY)
                .withModhash(MODHASH)
                .withRecipient(RECIPIENT)
                .withSubject(SUBJECT)
                .withCaptchaSolution(SOLUTION);

        underTest.build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWithLongSubject() {
        underTest.withMessageBody(MESSAGE_BODY)
                .withModhash(MODHASH)
                .withRecipient(RECIPIENT)
                .withSubject(subjectLength(101));

        underTest.build();
    }

    private String subjectLength(int size) {
        return StringUtils.rightPad("Title", size, '*');
    }
}
