package com.imanafrica.core.smsproviders.infobip;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.imanafrica.core.domain.Recipient;
import com.imanafrica.core.domain.Sms;
import com.imanafrica.core.domain.SmsResponse;
import com.imanafrica.core.smsproviders.SmsProvider;

@Component
public class InfobipSmsProvider implements SmsProvider {
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	Environment env;

	private static final String API_URL_JSON = "http://api.infobip.com/api/v3/sendsms/json";
	private static final String API_URL_XML = "http://api.infobip.com/api/v3/sendsms/xml";
	private static final String API_URL_COMMAND = "http://api.infobip.com/api/command?";

	// private static final String USERNAME = env.;
	// private static final String PASSWORD = "P@ssw0rd15";

	@Override
	public List<SmsResponse> sendSMS(Sms sms) {
		String requestObject = smsToXml(sms);

		RestTemplate template = new RestTemplate();
		String response = template.postForObject(API_URL_XML, requestObject,
				String.class);
		return marshallToResponseObjects(response);
	}

	@Override
	public List<SmsResponse> sendJsonSMS(Sms sms) {
		JSONObject requestObject = smsToJson(sms);

		RestTemplate template = new RestTemplate();
		String response = template.postForObject(API_URL_JSON,
				requestObject, String.class);
		return marshallJsonToResponseObjects(response);
	}

	private List<SmsResponse> marshallJsonToResponseObjects(String response) {
		log.info("The returned data is: " + response);	
		List<SmsResponse> results = new ArrayList<SmsResponse>();
		JSONParser parser = new JSONParser();
		
		try {
			JSONObject object = (JSONObject) parser.parse(response);
			JSONArray responses = (JSONArray) object.get("results");
			log.info(">>>>>>>>>>>>>> The returned data is: " + responses);
			log.info(">>>>>>>>>>>>>> The returned data size is: " + responses.size());	
			for(Object o : responses){
				JSONObject obj = (JSONObject) parser.parse(o.toString());
				log.info(">>>>>>>>>>>>>> The parsed object is: " + obj.toString());	
				SmsResponse r = new SmsResponse();
				r.setMessageid(obj.get("messageid").toString());
				r.setStatus(Integer.parseInt(obj.get("status").toString()));
				r.setDestination(Long.parseLong(obj.get("destination").toString()));
				results.add(r);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
 
		log.info(">>>>>>>>>>>>>> The returned responses object is : " + results.toString());	
		return results;
	}

	@SuppressWarnings("unchecked")
	private JSONObject smsToJson(Sms sms) {
		JSONObject object = new JSONObject();
		JSONObject authenticationObject = new JSONObject();
		authenticationObject.put("username",
				env.getProperty("infobip.user.name"));
		authenticationObject.put("password",
				env.getProperty("infobip.user.password"));
		object.put("authentication", authenticationObject);
		
		JSONArray messages = new JSONArray();
		for (Recipient r : sms.getRecipients()) {
			JSONObject messageObject = new JSONObject();
			messageObject.put("text", sms.getText());
			JSONArray recipients = new JSONArray();
			JSONObject recipient = new JSONObject();
			recipient.put("gsm", r.getCellnumber());
			recipient.put("messageId", sms.getMessageid());
			recipients.add(recipient);
			messageObject.put("recipients", recipients);
			messages.add(messageObject);
		}
		log.info(">>>>>>Created object is: " + object.toString());
		object.put("messages", messages);
		return object;
	}

	public double getCredits() {
		String credits = "0";
		String commandQuery = API_URL_COMMAND + "username="
				+ env.getProperty("infobip.user.name") + "&password="
				+ env.getProperty("infobip.user.password") + "&cmd=CREDITS";
		RestTemplate template = new RestTemplate();

		credits = template.getForObject(commandQuery, String.class);
		try {
			return Double.parseDouble(credits);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	private List<SmsResponse> marshallToResponseObjects(String response) {
		List<SmsResponse> responses = new ArrayList<SmsResponse>();
		log.info("Returned result: " + response);

		try {
			SAXBuilder builder = new SAXBuilder();
			log.info("Sax builder instance successfully created...");
			Document doc = builder.build(new StringReader(response));
			Element rootNode = doc.getRootElement();
			List<Element> elements = rootNode.getChildren();
			log.info("Returned elements: " + elements.size());

			for (Element e : elements) {
				SmsResponse item = new SmsResponse();
				item.setDestination(Long.parseLong(e
						.getChildText("destination")));
				item.setStatus(Integer.parseInt(e.getChildText("status")));
				item.setMessageid(e.getChildText("messageid"));
				System.out.println("Response: Message ID - "
						+ item.getMessageid() + ": Status - "
						+ item.getStatus() + ": Sent To - "
						+ item.getDestination());
				responses.add(item);
			}
			return responses;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	private String smsToXml(Sms sms) {
		StringBuilder xmlString = new StringBuilder();
		xmlString.append("XML=<SMS>");
		xmlString.append("<authentication>");
		xmlString.append("<username>" + env.getProperty("infobip.user.name")
				+ "</username>");
		xmlString.append("<password>"
				+ env.getProperty("infobip.user.password") + "</password>");
		xmlString.append("</authentication>");
		xmlString.append("<message>");
		// xmlString.append("<sender>" + sms.getSenderid() + "</sender>");
		xmlString.append("<text>" + sms.getText() + "</text>");
		if (sms.getType() != null && sms.getType().trim().length() > 0) {
			xmlString.append("<type>" + sms.getType() + "</type>");
		}
		xmlString.append("</message>");
		if (sms.getRecipients().size() > 0) {
			xmlString.append("<recipients>");
			for (Recipient recipient : sms.getRecipients()) {
				xmlString.append("<gsm messageid=\"" + sms.getMessageid()
						+ "\">" + recipient.getCellnumber() + "</gsm>");
			}
			xmlString.append("</recipients>");
		}
		xmlString.append("</SMS>");

		return xmlString.toString();
	}

}
