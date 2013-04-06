/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.lamson.thumbsy.appengine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that adds display number of devices and button to send a message.
 * <p>
 * This servlet is used just by the browser (i.e., not device) and contains the
 * main page of the demo app.
 */
@SuppressWarnings("serial")
public class TestServlet extends BaseServlet {

	static final String ATTRIBUTE_STATUS = "status";

	/**
	 * Displays the existing messages and offer the option to send a new one.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("application/json");

		PrintWriter writer = response.getWriter();
		// BufferedReader reader = req.getReader();
		// StringBuilder sb = new StringBuilder();
		// String line = reader.readLine();
		// while (line != null) {
		// sb.append(line + "\n");
		// line = reader.readLine();
		// }
		// reader.close();
		// String data = sb.toString();
		// writer.print(data);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					request.getInputStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			writer.print(buffer.toString());
		} finally {
			if (reader != null)
				reader.close();
			if (writer != null)
				writer.close();
		}

		response.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * Read Bing URL when make API call to get JSON data
	 * 
	 * @param urlString
	 * @return JSON data as a String
	 * @throws Exception
	 */
	// private String readBingUrl(String urlString) throws Exception {
	// Authenticator.setDefault(new BingAuthenticator());
	// BufferedReader reader = null;
	// try {
	// URL url = new URL(urlString);
	// reader = new BufferedReader(new InputStreamReader(url.openStream()));
	// StringBuffer buffer = new StringBuffer();
	// int read;
	// char[] chars = new char[1024];
	// while ((read = reader.read(chars)) != -1)
	// buffer.append(chars, 0, read);
	//
	// return buffer.toString();
	// } finally {
	// if (reader != null)
	// reader.close();
	// }
	// }
}