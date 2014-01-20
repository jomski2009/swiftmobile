package com.imanafrica.rest.v1.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.imanafrica.core.services.GroupService;
import com.imanafrica.rest.domain.GroupResource;
import com.imanafrica.rest.domain.RecipientResource;
import com.imanafrica.rest.domain.assemblers.GroupResourceAssembler;
import com.imanafrica.rest.domain.assemblers.RecipientResourceAssembler;
import com.imanafrica.rest.services.GroupRestService;

@RestController
@RequestMapping("api/v1/groups")
public class GroupRestController {
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	GroupRestService groupRestService;

	@Autowired
	GroupService groupService;

	@Autowired
	GroupResourceAssembler groupResourceAssembler;

	@Autowired
	RecipientResourceAssembler recipientResourceAssembler;

	@RequestMapping(value = "{userid}/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> createGroup(@RequestBody GroupResource group,
			@PathVariable("userid") int userid) {
		group = groupRestService.createGroup(userid, group);
		group.add(linkTo(UserRestController.class).slash("id").slash(userid)
				.withRel("owner"));

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	/**
	 * Add recipients to a specified user sms group. The json object must
	 * contain the group id as well as an array of recipient numbers and values
	 * wished to be associated with the number.
	 * 
	 * @param numbers
	 * @return ResponseEntity<Map<String, Object>>
	 */
	@RequestMapping(value = "{groupid}/addnumbers", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addRecipient(
			@PathVariable int groupid,
			@RequestBody(required = true) List<RecipientResource> numbers) {
		Map<String, Object> result = groupRestService.addToGroup(groupid,
				numbers);
		return new ResponseEntity<Map<String, Object>>(result,
				HttpStatus.CREATED);
	}

	@RequestMapping("{userid}/groups")
	@ResponseBody
	public ResponseEntity<List<GroupResource>> getGroups(
			@PathVariable int userid) {

		List<GroupResource> groups = groupResourceAssembler
				.toResources(groupService.getGroupsForUserByUserId(userid));
		return new ResponseEntity<List<GroupResource>>(groups, HttpStatus.OK);
	}
	
	@RequestMapping("group/{groupid}")
	public ResponseEntity<GroupResource> getGroupById(@PathVariable("groupid") int groupid){
		GroupResource group = groupResourceAssembler.toResource(groupService.getGroupById(groupid));
		if(group==null){
			return new ResponseEntity<GroupResource>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<GroupResource>(group, HttpStatus.OK);
	}

	@RequestMapping("group/{groupid}/members")
	public ResponseEntity<List<RecipientResource>> getGroupMembers(
			@PathVariable("groupid") int groupid) {
		List<RecipientResource> resources = recipientResourceAssembler
				.toResources(groupService.getGroupById(groupid).getMembers());
		return new ResponseEntity<List<RecipientResource>>(resources,
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "group/{groupid}/csv/addnumbers", method = RequestMethod.GET)
	public ResponseEntity<String> addMembersInfo(){
		String info = "You can upload a csv file to the specified group with the format - 'Cellnumber;value1;value2;value3;value4' by posting to this url \n The values must be semi-colon separated";
		return new ResponseEntity<String>(info, HttpStatus.OK);
	}
	

	@RequestMapping(value = "group/{groupid}/csv/addnumbers", method = RequestMethod.POST)
	public ResponseEntity<List<RecipientResource>> addMembersFromCSV(
			@PathVariable("groupid") int groupid, @RequestParam("file") MultipartFile file) {
		try {
			List<String> rows = new ArrayList<String>();
			List<RecipientResource> recipients = new ArrayList<RecipientResource>();

			if (!file.isEmpty()) {
				String row = "";
				BufferedReader br = new BufferedReader(new InputStreamReader(
						file.getInputStream()));
				while ((row = br.readLine()) != null) {
					rows.add(row);
				}
				br.close();
				rows.remove(0); //Just to get csv working... removing the header row...
				
				for(String r: rows){
					String[] recipientData = r.split(";");
					RecipientResource res = new RecipientResource();
					res.setCellnumber(Long.parseLong(recipientData[0]));
					res.setFirstvalue(recipientData[1]);
					res.setSecondvalue(recipientData[2]);
					res.setThirdvalue(recipientData[3]);
					res.setFourthvalue(recipientData[4]);
					recipients.add(res);
				}
				
				groupRestService.addToGroup(groupid,recipients);
				
				return new ResponseEntity<List<RecipientResource>>(recipients, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<RecipientResource>>(HttpStatus.BAD_REQUEST);

	}
}
