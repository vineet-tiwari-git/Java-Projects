package com.vineet.ssldebugger;

import java.io.IOException;
import java.io.OutputStream;

public class LogFileOutputStream extends OutputStream {

	private final StringBuilder sb = new StringBuilder();

	private final StringBuilder logs = new StringBuilder();

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
			logs.append(text);
			this.sb.setLength(0);
		} else {
			this.sb.append((char) b);
		}
	}

	@Override
	public String toString() {
		return logs.toString();
	}
}
