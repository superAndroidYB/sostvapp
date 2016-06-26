package activity.sostv.com.global;


public class CommonUtils {

	public static String embellish(String bookName){
		return "《"+bookName+"》";
	}
	
	public static String dataEmbellish(String data){
		return data.substring(5,16);
	}
}
