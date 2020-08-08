package com.security.authenticator.web;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.forward.RsForward;
import org.takes.facets.previous.RsPrevious;

import com.minlessika.utils.PreviousLocation;

public final class TkLogin implements Take {

	private final String suratiUrl;
	
	public TkLogin(final String suratiUrl) {
		this.suratiUrl = suratiUrl;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		return new RsPrevious(
			new RsForward(
				String.format("%s/login", suratiUrl)
			), 
			new PreviousLocation(req, true).toString()
		);
	}

}
