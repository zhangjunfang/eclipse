package cn.newcapec.framework.core.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import cn.newcapec.framework.core.model.datacontainer.DataContainer;
import cn.newcapec.framework.core.model.datacontainer.DataObject;
import cn.newcapec.framework.core.model.datacontainer.Property;

/**
 * 功能描述：应用框架中业务模型的简单扩展基类
 * andy.li
 */
@MappedSuperclass
public class CommonModelWithIntId extends DataContainer implements DataObject {

    public CommonModelWithIntId() {
        init(Base2ModelProperty.values());
    }

    public enum Base2ModelProperty implements Property {
        id(String.class);

        Class<?> type;

        Base2ModelProperty(Class<?> type) {
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
    @Column(name = "id")    
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="comm_seq")   
    @SequenceGenerator(name="comm_seq")  
    public String getId() {
        return (String) super.getValue(Base2ModelProperty.id);
    }

    public void setId(String id) {
        super.setValue(CommonModel.Base2ModelProperty.id, id);
    }

    /**
     * @param obj Object
     * @return boolean
     */
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof CommonModel) {
            String tempId = ((CommonModel) obj).getId();
            if (tempId == null) {
                return false;
            }
            return tempId.equals(this.getId());
        }
        return false;
    }
}