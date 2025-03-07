package com.vineet.ssldebugger;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

/**
 * {@link OutputStreamOutputStream} extension class to redirect the output to a
 * {@link JTextArea}
 *
 */
public class TextAreaOutputStream extends OutputStream {

	private final JTextArea		textArea;

	private final StringBuilder	sb	= new StringBuilder();

	public TextAreaOutputStream(final JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() {
	}

	@Override
	public void write(final int b) throws IOException {

		if (b == '\r') {
			return;
		}

		if (b == '\n') {
			final String text = this.sb.toString() + "\n";

			this.textArea.append(text);
			this.sb.setLength(0);
		} else {
			this.sb.append((char) b);
		}
	}
}
