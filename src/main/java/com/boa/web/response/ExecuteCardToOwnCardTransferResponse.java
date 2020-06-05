package com.boa.web.response;

public class ExecuteCardToOwnCardTransferResponse extends GenericResponse {
	
	//private String datetime;
	private String identifier;
	//private String type;
	//private String amount;
	private String ishold;
	//private String currency;
	 private String faultCode;
	    private String faultString;
	    
	    private Type type = new Type() ;

	    private Amount amount = new Amount() ;
	 
	public String getFaultCode() {
			return faultCode;
		}
		public void setFaultCode(String faultCode) {
			this.faultCode = faultCode;
		}
		public String getFaultString() {
			return faultString;
		}
		public void setFaultString(String faultString) {
			this.faultString = faultString;
		}
	/*public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}*/
	/*public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}*/
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Amount getAmount() {
		return amount;
	}
	public void setAmount(Amount amount) {
		this.amount = amount;
	}
	public String getIshold() {
		return ishold;
	}
	public void setIshold(String ishold) {
		this.ishold = ishold;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	private String mcc;
	
	
	
	 public class Type {
			
			private String identifier;
			private String description;
			public String getDescription() {
				return description;
			}
			public String getIdentifier() {
				return identifier;
			}
			public void setIdentifier(String identifier) {
				this.identifier = identifier;
			}
			public void setDescription(String description) {
				this.description = description;
			}
			
			
			
		}
	 
	 
	 
	 
	 public  class Amount {
			
			private Double amount;
			private String currency;
			public Double getAmount() {
				return amount;
			}
			public void setAmount(Double amount) {
				this.amount = amount;
			}
			public String getCurrency() {
				return currency;
			}
			public void setCurrency(String currency) {
				this.currency = currency;
			}
			
			
			
			
		}
	 
	

}



