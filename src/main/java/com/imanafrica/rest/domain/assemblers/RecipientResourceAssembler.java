package com.imanafrica.rest.domain.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.imanafrica.core.domain.Recipient;
import com.imanafrica.rest.domain.RecipientResource;
import com.imanafrica.rest.v1.controller.RecipientRestController;

@Component
public class RecipientResourceAssembler extends ResourceAssemblerSupport<Recipient, RecipientResource>{

	public RecipientResourceAssembler() {
		super(RecipientRestController.class, RecipientResource.class);
	}

	
	@Override
	protected RecipientResource instantiateResource(Recipient entity) {
		return super.instantiateResource(entity);
	}


	@Override
	public RecipientResource toResource(Recipient entity) {
		RecipientResource resource = new RecipientResource(entity);
		return resource;
	}

}
