package cn.newcapec.foundation.report.dao;

import java.util.List;

import cn.newcapec.foundation.report.model.DatasourceEntity;

public interface DatasourceDao {

	public abstract void addDsInfo(Object[] args);

	public abstract List<DatasourceEntity> getDsByParams(Object[] args,
			String name);

	public abstract int getDsCountByParams(Object[] args, String name);

	public abstract void deleteDsById(String id);

	public abstract int checkUniqueName(String name);

	public abstract void updateDsInfo(Object[] args);

	public abstract List<DatasourceEntity> getAllDsEntity();

	public abstract DatasourceEntity getDsEntityById(String id);

}