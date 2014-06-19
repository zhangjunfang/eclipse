package cn.newcapec.framework.core.model;

import cn.newcapec.framework.core.model.datacontainer.DataContainer;
import cn.newcapec.framework.core.model.datacontainer.DataObject;
import cn.newcapec.framework.core.model.datacontainer.Property;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 功能描述：应用框架中业务模型的简单扩展基类
 * andy.li
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class BaseModel extends DataContainer implements DataObject {

    public BaseModel() {
        init(BaseModelProperty.values());
    }

    public enum BaseModelProperty implements Property {
        id(String.class),
        createDate(Date.class),
        updateDate(Date.class),
        createBy(String.class),
        updateBy(String.class),
        version(Integer.class);

        Class<?> type;

        BaseModelProperty(Class<?> type) {
            this.type = type;
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public Class<?> getType() {
            return type;
        }
    }

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")

//    @GeneratedValue(generator = "paymentableGenerator")
//    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name = "id", nullable = false, length = 32)
    public String getId() {
        return (String)super.getValue(BaseModelProperty.id);
    }

    public void setId(String id) {
        super.setValue(BaseModel.BaseModelProperty.id, id);
    }


    /**
     * @return Date createDate 创建日期
     */
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return (Date)super.getValue(BaseModelProperty.createDate);
    }

    /**
     *
     * @param createDate 创建日期
     */
    public void setCreateDate(Date createDate) {
        super.setValue(BaseModelProperty.createDate, createDate);
    }

    /**
     * @return Date updateDate 更新日期
     */
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateDate() {
        return (Date)super.getValue(BaseModelProperty.updateDate);
    }

    /**
     *
     * @param updateDate 更新日期
     */
    public void setUpdateDate(Date updateDate) {
        super.setValue(BaseModelProperty.updateDate, updateDate);
    }

    /**
     * @return String createBy 创建人
     */
    @Column(name = "create_by", length = 32)
    public String getCreateBy() {
        return (String)super.getValue(BaseModelProperty.createBy);
    }

    /**
     *
     * @param createBy 创建人
     */
    public void setCreateBy(String createBy) {
        super.setValue(BaseModelProperty.createBy, createBy);
    }

    /**
     * @return String updateBy 更新人
     */
    @Column(name = "update_by", length = 32)
    public String getUpdateBy() {
        return (String)super.getValue(BaseModelProperty.updateBy);
    }

    /**
     *
     * @param updateBy 更新人
     */
    public void setUpdateBy(String updateBy) {
        super.setValue(BaseModelProperty.updateBy, updateBy);
    }

    /**
     * @return Integer 版本号。
     *  */
    @Column(name = "version")
    public Integer getVersion() {
        return (Integer)super.getValue(BaseModelProperty.version);
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        super.setValue(BaseModelProperty.version, version);
    }

    /**
     * @param obj
     *            Object
     * @return boolean
     */
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof BaseModel) {
            String tempId = ((BaseModel) obj).getId();
            if (tempId == null) {
                return false;
            }
            return tempId.equals(this.getId());
        }
        return false;
    }
}