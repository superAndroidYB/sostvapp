package activity.sostv.com.global;


import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import activity.sostv.com.model.SostvRequest;
import activity.sostv.com.model.SostvResponse;

public class WebServiceHelper extends AsyncTask<SostvRequest, Integer, SostvResponse> {


	@Override
	protected SostvResponse doInBackground(SostvRequest... requests) {

		SostvRequest request = requests[0];
		if(request == null){
			return null;
		}
		
		HttpTransportSE transport = new HttpTransportSE(Constants.sostvAppServiceUrl);
		
		SoapObject soapObject = new SoapObject(Constants.targetNameSpace, Constants.methodName);
		soapObject.addProperty("serviceType", request.getProperty(0));
		soapObject.addProperty("source", request.getProperty(1));
		soapObject.addProperty("data", request.getProperty(2));
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = soapObject;
		
		try {
			transport.call(Constants.targetNameSpace + Constants.methodName,
					envelope);
			SoapObject response = (SoapObject) envelope.bodyIn;
			Log.d("返回结果为：" + response.toString(), "SostvReponse");
			SostvResponse sostvResponse = new SostvResponse();
			sostvResponse.setSource(response.getProperty("source").toString());
			sostvResponse.setReturnCode(response.getProperty("returnCode").toString());
			sostvResponse.setReturnMessage(response.getProperty("returnMessage").toString());
			if(response.getProperty("returnData") != null){
				sostvResponse.setReturnData(response.getProperty("returnData").toString());
			}
			return sostvResponse;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return null;
		}
	}

}
