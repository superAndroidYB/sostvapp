package activity.sostv.com.model;

import java.io.Serializable;

/**
 * �ͻ�������ָ�����
 * @author Administrator
 *
 */
public class SosOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	private String orderType;
	
	private String orderId;

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
}
