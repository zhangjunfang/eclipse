/**
 * 
 */
package cn.newcapec.foundation.base.dialect;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle10gDialect;

/**
 * @author andy
 *
 */
public class NewcapecOracleDialect extends Oracle10gDialect    {   
	
	public NewcapecOracleDialect(){
		super();
        registerHibernateType( -1, Hibernate.STRING.getName());
        registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
        registerHibernateType(Types.DECIMAL, Hibernate.BIG_INTEGER.getName());  
        registerHibernateType(Types.LONGVARCHAR, 65535, "text");  
	}
}
