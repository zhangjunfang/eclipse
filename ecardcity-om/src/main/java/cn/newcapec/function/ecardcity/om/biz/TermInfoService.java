/**
 * 
 */
package cn.newcapec.function.ecardcity.om.biz;

import java.io.Serializable;

import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.function.ecardcity.om.model.TermInfo;

/**
 * @author ocean
 * email: zhangjunfang0505@163.com
 * QQ: 419692181
 * date: 2014-3-14
 */
public interface TermInfoService extends   BaseService<TermInfo> ,Serializable {

	TermInfo UpdateKEK(TermInfo info );

}
