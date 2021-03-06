/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.lamson.thumbsy.android;

import me.lamson.thumbsy.models.Sms;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

/**
 * Helper class providing methods and constants common to other classes in the
 * app.
 */
public final class CommonUtils {

	/**
	 * Base URL of the Demo Server (such as http://my_host:8080/gcm-demo)
	 */
	static final String SERVER_URL = "http://thumbsy-demo.appspot.com";

	static final String URL_POST_MESSAGE = "http://thumbsy-demo.appspot.com/api/messages";
	static final String URL_POST_CONVERSATION = "http://thumbsy-demo.appspot.com/api/conversations";
    static final String URL_POST_NOTIFY = "http://thumbsy-demo.appspot.com/notify";

	/**
	 * Google API project id registered to use GCM.
	 */
	static final String SENDER_ID = "1057121896515";

	/**
	 * Tag used on log messages.
	 */
	static final String TAG = "Thumbsy";

	/**
	 * Intent used to display a message in the screen.
	 */
	static final String DISPLAY_MESSAGE_ACTION = "me.lamson.thumbsy.android.DISPLAY_MESSAGE";

	static final String RECEIVE_SMS_ACTION = "me.lamson.thumbsy.android.RECEIVE_SMS";

	static final String RECEIVE_GCM_MESSAGE_ACTION = "me.lamson.thumbsy.android.RECEIVE_GCM_MESSAGE_ACTION";

	/**
	 * Intent's extra that contains the message to be displayed.
	 */
	static final String EXTRA_MESSAGE = "message";
	static final String EXTRA_ADDRESS = "address";

	static final Gson GSON = new Gson();

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the
	 * UI and the background service.
	 * 
	 * @param context
	 *            application's context.
	 * @param message
	 *            message to be displayed.
	 */
	static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		intent.putExtra(EXTRA_ADDRESS, "+12052085117");
		// intent.putExtra(EXTRA_ADDRESS, "+16823679168");
		// intent.putExtra(EXTRA_ADDRESS, "+12152067916");
		context.sendBroadcast(intent);
	}

	static void broadcastMsgFromGCM(Context context, String message,
			String address) {
		Intent intent = new Intent(RECEIVE_GCM_MESSAGE_ACTION);
		intent.putExtra(Sms.PROPERTY_BODY, message);
		intent.putExtra(Sms.PROPERTY_ADDRESS, address);
		context.sendBroadcast(intent);
	}
}
