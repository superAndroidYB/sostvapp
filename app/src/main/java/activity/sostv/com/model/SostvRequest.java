package activity.sostv.com.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class SostvRequest implements KvmSerializable {

	public String serviceType;
	public String source;
	public String data;

	public SostvRequest(){

	}

	public SostvRequest(String serviceType,String source,String data){
		setProperty(0,serviceType);
		setProperty(1,source);
		setProperty(2,data);
	}

	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
		case 0:
			return serviceType;
		case 1:
			return source;
		case 2:
			return data;
		default:
			break;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 3;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		switch (arg0) {
		case 0:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "serviceType";
			break;
		case 1:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "source";
			break;
		case 2:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "data";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch (arg0) {
		case 0:
			serviceType = arg1.toString();
			break;
		case 1:
			source = arg1.toString();
			break;
		case 2:
			data = arg1.toString();
			break;
		default:
			break;
		}
	}

}
