package org.subethamail.smtp.internal.command;

import org.subethamail.smtp.internal.server.BaseCommand;
import org.subethamail.smtp.server.Session;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Locale;

/**
 * @author Tomasz Balawajder
 */
public final class XClientCommand extends BaseCommand
{

	public XClientCommand()
	{
		super("XCLIENT", "Introduce yourself.", "<hostname>");
	}

	@Override
	public void execute(String commandString, Session sess) throws IOException
	{
		if (sess.isMailTransactionInProgress())
		{
			sess.sendResponse("503 Mail transaction in progress.");
			return;
		}

		String[] args = getArgs(commandString);
		if (args.length < 2)
		{
			sess.sendResponse("501 Syntax: XCLIENT 1*( SP attribute-name\"=\"attribute-value )");
			return;
		}

		for (String arg : args) {
			if (arg.toUpperCase(Locale.ENGLISH).startsWith("ADDR=")) {
				handleAddrAttribute(sess, arg.substring(5));
			}
		}

		sess.sendResponse(
				"220 " + sess.getServer().getHostName() + " ESMTP " + sess.getServer().getSoftwareName());
	}

	private void handleAddrAttribute(Session sess, String host) throws UnknownHostException {
		InetSocketAddress oldRemoteAddr = sess.getRemoteAddress();

		if (host.startsWith("IPV6:")) host = host.substring(5);

		InetAddress inetAddress = InetAddress.getByName(host);
		InetSocketAddress newRemoteAddr = new InetSocketAddress(inetAddress, oldRemoteAddr.getPort());

		sess.setRemoteAddress(newRemoteAddr);
	}
}
