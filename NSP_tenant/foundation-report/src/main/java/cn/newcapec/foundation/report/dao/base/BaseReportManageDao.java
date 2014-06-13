package cn.newcapec.foundation.report.dao.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import cn.newcapec.foundation.base.AppBaseDAO;
import cn.newcapec.foundation.report.dao.ReportManageDao;
import cn.newcapec.foundation.report.model.ReportManageEntity;
import cn.newcapec.framework.utils.Page;


@SuppressWarnings("all")
public class BaseReportManageDao extends AppBaseDAO<ReportManageEntity> {

	public Page queryReports(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer("select m.*,c.name as dcname from nano_report_manage m ");
		sb.append("left join nano_report_dc c on m.dc_id = c.id where 1=1 ");
		if (params.containsKey("name")
				&& StringUtils.isNotBlank(params.get("name").toString())) {
			sb.append(" and m.name like ? ");
			param.add(params.get("name"));
		}
		orderby.put("m.id", "asc");
		return this.sqlQueryForPage(sb.toString(), param.toArray(), orderby);
	}

	
	
}
