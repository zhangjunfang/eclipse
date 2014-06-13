package cn.newcapec.framework.core.model;

import cn.newcapec.framework.core.model.datacontainer.DataContainer;
import cn.newcapec.framework.core.model.datacontainer.DataObject;
import cn.newcapec.framework.core.model.datacontainer.Property;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 功能描述：应用框架中业务模型的简单扩展基类
 * andy.li
 */
@MappedSuperclass
public class CommonModel extends DataContainer implements DataObject {

    public CommonModel() {
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
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", nullable = false, length = 32)
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