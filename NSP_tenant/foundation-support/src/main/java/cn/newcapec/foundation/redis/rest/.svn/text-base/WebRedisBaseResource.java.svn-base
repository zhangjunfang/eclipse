package cn.newcapec.foundation.redis.rest;

import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;

import net.sf.json.JSONObject;
import cn.newcapec.framework.base.rest.BaseRequest;
import cn.newcapec.framework.base.rest.BaseResource;
import cn.newcapec.framework.base.rest.BaseResourceHandler;
import cn.newcapec.framework.base.rest.BaseResponse;
import cn.newcapec.framework.base.rest.Msg;

@SuppressWarnings("all")
public class WebRedisBaseResource extends BaseResource implements BaseResourceHandler{
	
	public void getjsonParms(BaseRequest request,BaseResponse response){
		 Msg msg = new Msg();
		 if(!getJsonObject().getBoolean("success")){
			 msg.setSuccess(false);
			msg.setMsg("格式不正确，解析失败！");
			response.print(msg.toJSONObjectPresention());
		 }
	 }

}
