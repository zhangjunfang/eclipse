package cn.newcapec.function.ecardcity.om.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.id.UUIDGenerator;
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
@Component(value="collectResource")
@Scope("prototype")
public class CollectResource extends BaseResource implements
		BaseResourceHandler, Serializable {

	private static final long serialVersionUID = -7865679842672072786L;
	
	public void getIndustryList(BaseRequest request, BaseResponse response) {
		TermInfo  info2=service.get("111");
		System.out.println("info2.getKekcode()=====");
		List<String> list = new ArrayList<String>(30);
		for (int i = 0; i < 30; i++) {
			list.add(i+"");
		}
		Msg msg = new Msg();
		msg.setSuccess(true);
		msg.setMsg("dempartment Item");
		msg.setData(list);
		response.print(msg.toJSONObjectPresention());
	}

	@Autowired
	private TermInfoService service;
	
	
	public void updateKET(BaseRequest request, BaseResponse response) {
		JSONObject jsonObject= getJsonObject();
		TermInfo info=(TermInfo)jsonObject.toBean(jsonObject, TermInfo.class);
		info.setId(new UUIDGenerator().toString());
		System.out.println("========================================="+info.getId());
		service.saveOrUpdate(info);
		Msg msg = new Msg();
		msg.setSuccess(true);
		msg.setMsg("dempartment Item");
		msg.setData("fsdklfjsldkfs");
		response.print(msg.toJSONObjectPresention());
	}
	
}
