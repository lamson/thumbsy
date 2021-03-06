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

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.lamson.thumbsy.appengine.dao.DatastoreGCM;
import me.lamson.thumbsy.appengine.dao.SmsDao;
import me.lamson.thumbsy.appengine.dao.SmsThreadDao;
import me.lamson.thumbsy.models.Sms;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;

/**
 * Servlet that adds a new message to all registered devices.
 * <p>
 * This servlet is used just by the browser (i.e., not device).
 */
@SuppressWarnings("serial")
public class SendAllMessagesServlet extends BaseServlet {

	/**
	 * Processes the request to add a new message.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		List<String> devices = DatastoreGCM.getDevices();
		String status;
		if (devices.isEmpty()) {
			status = "Message ignored as there is no device registered!";
		} else {

			String jsonData = readJsonRequest(req);
			Sms msg = GSON.fromJson(jsonData, Sms.class);
			String msgBody = msg.getBody();
			String msgAddress = msg.getAddress();
			String msgUserId = msg.getUserId();
			String deviceRegId = DatastoreGCM.getRegIdByUserId(msgUserId);
			
			String msgThreadId = msgAddress + msgUserId;

			// NOTE: when succeed sending message, save to datastore
			// should complete by android client, when message sent,
			// android post, then real time API fetch

			// check for conversation existed or not
			if (SmsThreadDao.getThreadById(msgThreadId) == null) {
				SmsThreadDao.createAndStoreThread(msgThreadId, msgUserId,
						msgAddress);
			}

			SmsDao.storeSms(msg);

			Queue queue = QueueFactory.getQueue("gcm");
			queue.add(withUrl("/send")
					.param(SendMessageServlet.PARAMETER_DEVICE, deviceRegId)
					.param(SendMessageServlet.PARAMETER_MESSAGE, msgBody)
					.param(SendMessageServlet.PARAMETER_ADDRESS, msgAddress));
			status = "Single message queued for registration id: \n"
					+ deviceRegId + "\nMessage Content: " + msgBody;

			// NOTE: check below is for demonstration purposes; a real
			// application
			// could always send a multicast, even for just one recipient
			// if (devices.size() == 1) {
			// // send a single message using plain post
			// String device = devices.get(0);
			// queue.add(withUrl("/send").param(
			// SendMessageServlet.PARAMETER_DEVICE, device).param(
			// SendMessageServlet.PARAMETER_MESSAGE, messageContent));
			// status = "Single message queued for registration id: \n"
			// + device + "\nMessage Content: " + messageContent;
			// }
			// } else {
			// send a multicast message using JSON
			// must split in chunks of 1000 devices (GCM limit)
			// int total = devices.size();
			// List<String> partialDevices = new ArrayList<String>(total);
			// int counter = 0;
			// int tasks = 0;
			// for (String device : devices) {
			// counter++;
			// partialDevices.add(device);
			// int partialSize = partialDevices.size();
			// if (partialSize == DatastoreGCM.MULTICAST_SIZE
			// || counter == total) {
			// String multicastKey = DatastoreGCM
			// .createMulticast(partialDevices);
			// logger.fine("Queuing " + partialSize
			// + " devices on multicast " + multicastKey);
			// TaskOptions taskOptions = TaskOptions.Builder
			// .withUrl("/send")
			// .param(SendMessageServlet.PARAMETER_MULTICAST,
			// multicastKey)
			// .param(SendMessageServlet.PARAMETER_MESSAGE,
			// messageContent).method(Method.POST);
			// queue.add(taskOptions);
			// partialDevices.clear();
			// tasks++;
			// }
			// // }
			// status = "Queued tasks to send " + tasks
			// + " multicast messages to " + total + " devices";
			// }
		}
		// req.setAttribute(HomeServlet.ATTRIBUTE_STATUS, status.toString());
		// getServletContext().getRequestDispatcher("/home").forward(req, resp);
	}
}
