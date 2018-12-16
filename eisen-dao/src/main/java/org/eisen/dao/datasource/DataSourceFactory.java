package org.eisen.dao.datasource;

import javax.sql.DataSource;

public interface DataSourceFactory {

    DataSource getDataSource();

    DataSource getDataSource(Class clz);

}
