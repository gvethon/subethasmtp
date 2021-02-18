/*
 * Commands.java Created on November 18, 2006, 12:26 PM To change this template,
 * choose Tools | Template Manager and open the template in the editor.
 */

package org.subethamail.smtp.internal.server;

import org.subethamail.smtp.internal.command.AuthCommand;
import org.subethamail.smtp.internal.command.BdatCommand;
import org.subethamail.smtp.internal.command.DataCommand;
import org.subethamail.smtp.internal.command.EhloCommand;
import org.subethamail.smtp.internal.command.ExpandCommand;
import org.subethamail.smtp.internal.command.HelloCommand;
import org.subethamail.smtp.internal.command.HelpCommand;
import org.subethamail.smtp.internal.command.MailCommand;
import org.subethamail.smtp.internal.command.NoopCommand;
import org.subethamail.smtp.internal.command.QuitCommand;
import org.subethamail.smtp.internal.command.ReceiptCommand;
import org.subethamail.smtp.internal.command.ResetCommand;
import org.subethamail.smtp.internal.command.StartTLSCommand;
import org.subethamail.smtp.internal.command.VerifyCommand;
import org.subethamail.smtp.internal.command.XClientCommand;

/**
 * Enumerates all the Commands made available in this release.
 *
 * @author Marco Trevisan <mrctrevisan@yahoo.it>
 */
public enum CommandRegistry
{
	AUTH(new AuthCommand(), true, false),
	DATA(new DataCommand(), true, true),
	EHLO(new EhloCommand(), false, false),
	HELO(new HelloCommand(), true, false),
	HELP(new HelpCommand(), true, true),
	MAIL(new MailCommand(), true, true),
	NOOP(new NoopCommand(), false, false),
	QUIT(new QuitCommand(), false, false),
	RCPT(new ReceiptCommand(), true, true),
	RSET(new ResetCommand(), true, false),
	STARTTLS(new StartTLSCommand(), false, false),
	VRFY(new VerifyCommand(), true, true),
	EXPN(new ExpandCommand(), true, true),
	BDAT(new BdatCommand(), true, true),
	XCLIENT(new XClientCommand(), false, false);

	private final Command command;

	private CommandRegistry(Command cmd, boolean checkForStartedTLSWhenRequired, boolean checkForAuthIfRequired)
	{
		final Command c;
        if (checkForStartedTLSWhenRequired)
			c = new RequireTLSCommandWrapper(cmd);
		else
			c= cmd;
        if (checkForAuthIfRequired)
            this.command = new RequireAuthCommandWrapper(c);
        else 
            this.command = c;
	}

	public Command getCommand()
	{
		return this.command;
	}
}
