/**
 * 
 */
package cn.newcapec.foundation.base.dialect;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;

/**
 * @author andy
 *
 */
public class NewcapecMySQLDialect extends MySQL5Dialect    {   
	
	public NewcapecMySQLDialect(){
		super();
        registerHibernateType( -1, Hibernate.STRING.getName());
        registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
        registerHibernateType(Types.DECIMAL, Hibernate.BIG_INTEGER.getName());  
        registerHibernateType(Types.LONGVARCHAR, 65535, "text");  
	}
}
