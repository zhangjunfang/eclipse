/**
 * 
 */
package cn.newcapec.function.ecardcity.om.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.newcapec.framework.core.handler.MultiViewResource;
import cn.newcapec.framework.core.rest.Msg;

/**
 * @author ocean 
 * email: zhangjunfang0505@163.com 
 * QQ: 419692181 
 * date: 2014-2-21
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/collectService")
public class CollectController extends MultiViewResource {
	@ResponseBody
	@RequestMapping(value = "collect/addIndustryUI",method={RequestMethod.POST,RequestMethod.GET})
	public Msg getIndustryList() {
		List<String> list = new ArrayList<String>(30);
		for (int i = 0; i < 30; i++) {
			list.add(i+"");
		}
		Msg msg = new Msg();
		msg.setSuccess(true);
		msg.setMsg("dempartment Item");
		msg.setData(list);
		msg.toJSONObjectPresention();
		return msg;
	}
}
