package activity.sostv.com.model;


public class SostvResponse {

	private String source;
	private String returnCode;
	private String returnMessage;
	private String returnData;
	
	public SostvResponse() {
		
	}
	
	public SostvResponse(String source, String returnCode,String returnMessage){
		this(source, returnCode, returnMessage, null);
	}
	public SostvResponse(String source, String returnCode,String returnMessage,String returnData){
		this.source = source;
		this.returnCode = returnCode;
		this.returnMessage = returnMessage;
		this.returnData = returnData;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public String getReturnData() {
		return returnData;
	}

	public void setReturnData(String returnData) {
		this.returnData = returnData;
	}
	
	
}
