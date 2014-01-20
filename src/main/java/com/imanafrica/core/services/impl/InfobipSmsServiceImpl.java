package com.imanafrica.core.services.impl;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imanafrica.core.dao.AccountDao;
import com.imanafrica.core.dao.RecipientDao;
import com.imanafrica.core.dao.SmsDao;
import com.imanafrica.core.dao.SmsResponseDao;
import com.imanafrica.core.dao.UserDao;
import com.imanafrica.core.domain.Account;
import com.imanafrica.core.domain.Recipient;
import com.imanafrica.core.domain.Sms;
import com.imanafrica.core.domain.SmsResponse;
import com.imanafrica.core.domain.User;
import com.imanafrica.core.services.SmsService;
import com.imanafrica.core.smsproviders.SmsProvider;
import com.imanafrica.rest.dto.RecipientDTO;
import com.imanafrica.rest.dto.SmsDTO;

@Service
public class InfobipSmsServiceImpl implements SmsService {

	protected Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private SmsProvider smsServiceProvider;

	@Autowired
	SmsDao smsDao;

	@Autowired
	UserDao userDao;

	@Autowired
	AccountDao accountDao;

	@Autowired
	RecipientDao recipientDao;

	@Autowired
	SmsResponseDao smsResponseDao;

	@Transactional
	@Override
	public List<SmsResponse> sendSms(SmsDTO sms, String username) {
		// Transform the object into a usable sms object...
		Sms newSms = new Sms();
		newSms.setText(sms.getText());
		newSms.setMessageid(sms.getMessageid());
		newSms.setType(sms.getType());
		Set<Recipient> recipients = new HashSet<Recipient>();
		for (RecipientDTO dto : sms.getRecipients()) {
			Recipient r = new Recipient();
			r.setCellnumber(dto.getCellnumber());
			recipients.add(r);
		}
		newSms.setRecipients(recipients);
		return sendSms(newSms, username);
	}

	@Override
	@Transactional
	public List<SmsResponse> sendSms(Sms sms, String username) {
		int numberOfRecipients = sms.getRecipients().size();
		User user = userDao.findByUsername(username);

		if (enoughCreditsToSendSms(user, numberOfRecipients)) {
			// Send the smses...
			List<SmsResponse> responses = smsServiceProvider.sendJsonSMS(sms);

			// Assume dto objects...
			// Build full association graph..
			Sms newSms = new Sms();

			Iterator<Recipient> itr = sms.getRecipients().iterator();

			while (itr.hasNext()) {
				Recipient oldrec = itr.next();
				Recipient newrec = saveOrUpdate(oldrec);
				log.debug("+++++++++++ Recipient : " + newrec.getCellnumber() + " Id: " + newrec.getRecipientid());
				newSms.getRecipients().add(newrec);
			}
			
			log.debug(">>>>>>>>++++++ Finished processing recipients");

			newSms.setSentdate(new GregorianCalendar().getTime());
			newSms.setText(sms.getText());
			newSms.setMessageid(sms.getMessageid());
			newSms.setUser(user);
			log.debug("The set user for the sms is " + user.getFirstname());
			log.debug("The sms is :" + newSms.getText());
			newSms = smsDao.save(newSms);

			updateAccountBalance(user, numberOfRecipients);
			log.debug(">>>>>>>>++++++ Account successfully updated");

			// We should also save the responses...
			// First attempt...
			Iterator<SmsResponse> itrResponse = responses.iterator();
			while (itrResponse.hasNext()) {
				itrResponse.next().setTargetSms(newSms);
			}
			smsResponseDao.save(responses);
			return responses;

		} else {
			log.info("We cannot process the sms");
			return null;
		}
	}

	private void updateAccountBalance(User user, int numberOfRecipients) {
		Iterator<Account> itr = user.getAccounts().iterator();
		Account account = new Account();
		if (itr.hasNext()) {
			account = itr.next(); // Take the first account...
		}
		log.info("Account retrieved: " + account.getName());

		double balance = account.getBalance();
		double smsvalue = account.getSmsvalue();

		double amountUsed = numberOfRecipients * smsvalue;
		account.setBalance(balance - amountUsed);
		accountDao.saveAndFlush(account);
	}

	private Recipient saveOrUpdate(Recipient oldrec) {
		if (recipientDao.findByCellnumber(oldrec.getCellnumber()) != null) {
			log.info("A recipient object was found: "
					+ recipientDao.findByCellnumber(oldrec.getCellnumber())
							.toString());
			return recipientDao.findByCellnumber(oldrec.getCellnumber());
		} else {
			oldrec.setDateadded(new GregorianCalendar().getTime());
			return recipientDao.save(oldrec);
		}
	}

	private boolean enoughCreditsToSendSms(User user, int numberOfRecipients) {
		Iterator<Account> itr = user.getAccounts().iterator();
		Account account = new Account();
		if (itr.hasNext()) {
			account = itr.next(); // Take the first account...
		}
		log.info("Account retrieved: " + account.getName());

		double balance = account.getBalance();
		double smsvalue = account.getSmsvalue();

		double amountNeeded = numberOfRecipients * smsvalue;
		if (balance > amountNeeded) {
			return true;
		}
		return false;
	}

	@Override
	public double getCredits() {
		return smsServiceProvider.getCredits();
	}

}
