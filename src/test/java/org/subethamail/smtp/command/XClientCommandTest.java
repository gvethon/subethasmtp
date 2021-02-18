package org.subethamail.smtp.command;

import org.subethamail.smtp.util.ServerTestCase;

/**
 * @author Tomasz Balawajder
 */
public class XClientCommandTest extends ServerTestCase {

    public XClientCommandTest(String name) {
        super(name);
    }

    public void testCommandHandling() throws Exception
    {
        this.expect("220");

        this.send("XCLIENT ADDR=10.158.189.139 NAME=[UNAVAILABLE]");
        this.expect("220");
    }

    public void testCommandCanNotBeSentInTheMiddleOfMailTransaction() throws Exception
    {
        this.expect("220");

        this.send("MAIL FROM: success@subethamail.org");
        this.expect("250 Ok");

        this.send("XCLIENT ADDR=10.158.189.139 NAME=[UNAVAILABLE]");
        this.expect("503 Mail transaction in progress.");
    }
}
