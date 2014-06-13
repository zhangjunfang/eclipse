/**
 * 
 */
package cn.newcapec.function.ecardcity.om.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.function.ecardcity.om.biz.TermInfoService;
import cn.newcapec.function.ecardcity.om.dao.TermInfoDAO;
import cn.newcapec.function.ecardcity.om.model.TermInfo;

/**
 * @author ocean 
 * email: zhangjunfang0505@163.com 
 * QQ: 419692181 
 * date: 2014-3-14
 */
@Service("termInfoService")
@Transactional
public class TermInfoServiceImpl implements TermInfoService {

	private static final long serialVersionUID = 8105741350205571736L;

	@Autowired
	private TermInfoDAO dao;

	@Override
	@Transactional(readOnly=true,propagation = Propagation.NOT_SUPPORTED)
	public TermInfo get(String id) {

		return dao.get(id);
	}

	@Override
	public void removeUnused(String id) {
		dao.delete(id);
	}

	@Override
	public void saveOrUpdate(TermInfo termInfo) {
		dao.saveOrUpdate(termInfo);
	}

	
	@Override
	public TermInfo UpdateKEK(TermInfo info) {
		
		TermInfo termInfo=dao.get(info.getId());
		if (null!=termInfo) {
			return termInfo;
		}else {
			dao.saveOrUpdate(info);
			return dao.get(info.getId());
		}
	}

}
