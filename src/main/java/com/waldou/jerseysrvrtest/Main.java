/**
 * The MIT License
 *
 * Copyright (c) 2018 Waldo J. Urribarri. http://www.waldou.com/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.waldou.jerseysrvrtest;

import org.apache.log4j.Logger;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

/**
 * @author Waldo Urribarri - www.waldou.com
 *
 * Point of entry of the server.
 *
 */
public class Main {

	private static Logger LOG = Logger.getLogger(Main.class);
	public static final String BASE_URL = "http://localhost:%PORT_NUMBER%/";
	
	public static void main(String[] args) throws Exception {
		HttpServer httpServer = createHttpServer(args);
		if(httpServer != null)
			httpServer.start();
	}

	private static HttpServer createHttpServer(String[] args) throws Exception {
		String url = BASE_URL;
		if(args.length == 0) {
			LOG.error("Missing parameter: PORT_NUMBER");
			return null;
		} else {
			try {
				url = url.replace("%PORT_NUMBER%", Integer.parseInt(args[0])+"");
			} catch(Exception e) {
				LOG.error("Invalid port number: " + args[0]);
				throw new IllegalArgumentException("Invalid port number.", e);
			}
		}
		return HttpServerFactory.create(url);
	}

}
