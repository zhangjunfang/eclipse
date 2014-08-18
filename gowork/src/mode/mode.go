// mode project mode.go
package mode
import (
	"time"
)
//心跳请求
type HeartbeatREQ  struct{
	AccountID  string "json:'accountID'";//用户名，使用分配给客户端的注册码，长度为12位 【规则由郑州研发提供】
	HardwareCode string "json:'hardwareCode'";//DES加密后的硬件信息码(原始信息基本规则cpuid+硬盘id+mac)
	AuthString string "json:'authString'";//原始密码为8位，不足8位在前面补0；DES加密后输出，算法为： des(accountID+hardwareCode+Password+timeStamp) 服务端解密后核对硬件信息码和时间戳;
	TimeStamp  string "json:'timeStamp'";//时间戳
}

type HeartbeatRES  struct{
	Result  string  "json:'result'";//心跳响应结果，0为成功，其余值为错误。
	Desc1 string "json:'desc1'";//Sftp服务器IP(经过DES加密)
	Desc2 string "json:'desc2'";//Sftp服务端口(经过DES加密)
	Desc3  string "json:'desc3'";// Sftp用户名(经过DES加密)
	Desc4 string "json:'desc4'";//Sftp密码串(经过DES加密)
}
func (req *HeartbeatREQ) String() (str string){
	return " {accountID:"+req.AccountID+" , "+"hardwareCode:"+req.HardwareCode+" ,"+"authString:"+req.AuthString+" ,"+"timeStamp:"+req.TimeStamp+"}";
}
func (res *HeartbeatRES) String() (str string){
	return " {result:"+res.Result+" , "+"desc1:"+res.Desc1+" ,"+"desc2:"+res.Desc2+" ,"+"desc3:"+res.Desc3+" ,"+"desc4: "+res.Desc4+"}";
}

//资源同步请求消息
type ResSyncREQ  struct{
	AccessToken  string "json:'accessToken'";//登录响应消息中中心管理平台返回客户端节点的认证Token，长度22字节
	TID string "json:'tID'";//操作交易序列号，由生成，长度12字节
	TimeStamp time.Time "json:'timeStamp'";//消息发送时间（采用北京时间）
	ResVersion  string "json:'resVersion'";//当前资源版本.
}

type ResSyncRES  struct{
	Isupdate  string "json:'isupdate'";//登录响应消息中中心管理平台返回客户端节点的认证Token，长度22字节
	TID string "json:'tID'";// 操作交易序列号，同请求消息
	MsgResult string "json:'msgResult'";//是否有更新,0-无 1-有更新
	ResVersion  string "json:'resVersion'";//最新版本号;有更新时填写 客户端得到有更新的反馈以后，根据反馈的版本号去SFTP指定目录下载，并应用，之后反馈应用成功的消息
}
func (req *ResSyncREQ) String() (str string){
	return " {accessToken:"+req.AccessToken+" , "+"tID:"+req.TID+" ,"+"timeStamp:"+req.TimeStamp.String()+" ,"+"resVersion:"+req.ResVersion+"}";
   //return " {accessToken:"+req.AccessToken+" , "+"tID:"+req.TID+" ,"+"resVersion:"+req.ResVersion+"}";
}
func (res *ResSyncRES) String() (str string){
	return " {isupdate:"+res.Isupdate+" , "+"tID:"+res.TID+" ,"+"msgResult:"+res.MsgResult+" ,"+"resVersion:"+res.ResVersion+"}";
}
//====资源应用成功通知
type ResLoadSuccessNotifyREQ  struct{
	AccessToken  string "json:'accessToken'";//登录响应消息中中心管理平台返回客户端节点的认证Token，长度22字节
	TID string "json:'tID'";//操作交易序列号，由生成，长度12字节
	TimeStamp time.Time "json:'timeStamp'";//消息发送时间（采用北京时间）
	ResVersion  string "json:'resVersion'";//当前资源版本.
}

type ResLoadSuccessNotifyRES  struct{
	TID string "json:'tID'";// 操作交易序列号，同请求消息
	MsgResult string "json:'msgResult'";//是否有更新,0-无 1-有更新

}
func (req *ResLoadSuccessNotifyREQ) String() (str string){
	return " {accessToken:"+req.AccessToken+" , "+"tID:"+req.TID+" ,"+"timeStamp:"+req.TimeStamp.String()+" ,"+"resVersion:"+req.ResVersion+"}";
}
func (res *ResLoadSuccessNotifyRES) String() (str string){
	return " {tID:"+res.TID+" ,"+"msgResult:"+res.MsgResult+"}";
}

//====注册码状态查询
type LicenseStatusQueryREQ  struct{
	AccessToken  string "json:'accessToken'";//登录响应消息中中心管理平台返回客户端节点的认证Token，长度22字节
	TID string "json:'tID'";//操作交易序列号，由生成，长度12字节
	TimeStamp time.Time "json:'timeStamp'";//消息发送时间（采用北京时间）
	AccountID  string "json:'accountID'";//当前资源版本.
}

type LicenseStatusQueryRES  struct{
	TID string "json:'tID'";// 操作交易序列号，同请求消息
	MsgResult string "json:'msgResult'";//是否有更新,0-无 1-有更新
	Status  string  "json:'status'";
	Expirationdate time.Time  "json:'expirationdate'";

}
func (req *LicenseStatusQueryREQ) String() (str string){
	return " {accessToken:"+req.AccessToken+" , "+"tID:"+req.TID+" ,"+"timeStamp:"+req.TimeStamp.String()+" ,"+"accountID:"+req.AccountID+"}";
}
func (res *LicenseStatusQueryRES) String() (str string){
	return " {tID:"+res.TID+" ,"+"msgResult:"+res.MsgResult+","+"status:"+res.Status+","+"expirationdate:"+res.Expirationdate.String()+"}";
}

//======配置同步请求
type ConfigSyncREQ  struct{
	AccessToken  string "json:'accessToken'";//登录响应消息中中心管理平台返回客户端节点的认证Token，长度22字节
	TID string "json:'tID'";//操作交易序列号，由生成，长度12字节
	TimeStamp time.Time "json:'timeStamp'";//消息发送时间（采用北京时间）
	ConfigVersion  string "json:'configVersion'";//当前资源版本.
}

type ConfigSyncRES  struct{
	TID string "json:'tID'";// 操作交易序列号，同请求消息
	MsgResult string "json:'msgResult'";//是否有更新,0-无 1-有更新
	Isupdate  string  "json:'isupdate'";
	ResVersion string "json:'resVersion'";
	Timecontrol string "json:'timecontrol'";
	Filtercontrol string  "json:'filtercontrol'";

}
func (req *ConfigSyncREQ) String() (str string){
	return " {accessToken:"+req.AccessToken+" , "+"tID:"+req.TID+" ,"+"timeStamp:"+req.TimeStamp.String()+" ,"+"configVersion:"+req.ConfigVersion+"}";
}
func (res *ConfigSyncRES) String() (str string){
	return " {tID:"+res.TID+" ,"+"msgResult:"+res.MsgResult+","+"isupdate:"+res.Isupdate+","+"resVersion:"+res.ResVersion+","+"timecontrol:"+res.Timecontrol+","+"filtercontrol:"+res.Filtercontrol+"}";
}