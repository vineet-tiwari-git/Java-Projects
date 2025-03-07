package com.vineet.ssldebugger.debug;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.util.LangUtils;

/**
 * @author <a href="mailto:tiwari_vineet@hotmail.com">Vineet Tiwari</a>
 * 
 */
public class LocalUsernamePasswordCredentials implements Credentials {
	private String	userName;
	private String	password;

	public LocalUsernamePasswordCredentials(final String usernamePassword) {
		if (usernamePassword == null) {
			throw new IllegalArgumentException(
					"Username:password string may not be null");
		}
		final int atColon = usernamePassword.indexOf(58);
		if (atColon >= 0) {
			this.userName = usernamePassword.substring(0, atColon);
			this.password = usernamePassword.substring(atColon + 1);
		} else {
			this.userName = usernamePassword;
		}
	}

	public LocalUsernamePasswordCredentials(final String userName, final String password) {
		if (userName == null) {
			throw new IllegalArgumentException("Username may not be null");
		}
		this.userName = userName;
		this.password = password;
	}

	/** @deprecated */
	@Deprecated
	public void setUserName(final String userName) {
		if (userName == null) {
			throw new IllegalArgumentException("Username may not be null");
		}
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	/** @deprecated */
	@Deprecated
	public void setPassword(final String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	@Override
	public String toString() {
		final StringBuffer result = new StringBuffer();
		result.append(this.userName);
		result.append(":");
		result.append((this.password == null) ? "null" : this.password);
		return result.toString();
	}

	@Override
	public int hashCode() {
		int hash = LangUtils.HASH_SEED;
		hash = LangUtils.hashCode(hash, this.userName);
		hash = LangUtils.hashCode(hash, this.password);
		return hash;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}

		if (o.getClass() == super.getClass()) {
			final LocalUsernamePasswordCredentials that = (LocalUsernamePasswordCredentials) o;

			if ((LangUtils.equals(this.userName, that.userName))
					&& (LangUtils.equals(this.password, that.password))) {
				return true;
			}
		}
		return false;
	}
}
