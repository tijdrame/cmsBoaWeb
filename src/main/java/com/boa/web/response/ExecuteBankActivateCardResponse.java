package com.boa.web.response;

import java.util.ArrayList;
import java.util.List;

import com.boa.web.response.prepareChangeCardOption.Information;

public class ExecuteBankActivateCardResponse extends GenericResponse  {
	
	private String businessfault;
	private String faultstring;
	String action ;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getFaultstring() {
		return faultstring;
	}
	public void setFaultstring(String faultstring) {
		this.faultstring = faultstring;
	}
	
	
	
	
	
	
	
	 private List<Information> stringInput = new ArrayList<>();
		
		
		public List<Information> getStringInput() {
			return stringInput;
		}

		public String getBusinessfault() {
			return businessfault;
		}



		public void setBusinessfault(String businessfault) {
			this.businessfault = businessfault;
		}

		
		public class StringInput {

			
			private String identifier;
			
			public String getIdentifier() {
				return identifier;
			}

			public void setIdentifier(String identifier) {
				this.identifier = identifier;
			}

			public String getStereotype() {
				return stereotype;
			}

			public void setStereotype(String stereotype) {
				this.stereotype = stereotype;
			}

			public String getLabel() {
				return label;
			}

			public void setLabel(String label) {
				this.label = label;
			}

			public String getDescription() {
				return description;
			}

			public void setDescription(String description) {
				this.description = description;
			}

			public String getPlaceholder() {
				return placeholder;
			}

			public void setPlaceholder(String placeholder) {
				this.placeholder = placeholder;
			}

			private String stereotype;
			
			private String label;
			
			private String description;

			private String placeholder;
			

	}
	}


	


