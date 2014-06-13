package cn.newcapec.function.ecardcity.om.rest;

import java.io.Serializable;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.newcapec.framework.core.rest.BaseRequest;
import cn.newcapec.framework.core.rest.BaseResource;
import cn.newcapec.framework.core.rest.BaseResourceHandler;
import cn.newcapec.framework.core.rest.BaseResponse;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.function.ecardcity.om.biz.TermInfoService;
import cn.newcapec.function.ecardcity.om.model.TermInfo;
@SuppressWarnings("all")
@Component(value="termInfoResource")
@Scope("prototype")
public class TermInfoResource extends BaseResource implements
		BaseResourceHandler, Serializable {

	private static final long serialVersionUID = -7865679842672072786L;

	@Autowired
	private TermInfoService service;
	
	
	public void updateKET(BaseRequest request, BaseResponse response) {
		//System.out.println("=========================================");
		JSONObject jsonObject= getJsonObject();
		TermInfo info=(TermInfo)jsonObject.toBean(jsonObject, TermInfo.class);
		service.saveOrUpdate(info);
		
		Msg msg = new Msg();
		msg.setSuccess(true);
		msg.setMsg("dempartment Item");
		msg.setData("fsdklfjsldkfs");
		response.print(msg.toJSONObjectPresention());
	}

	
}
