package com.mitocode.dao;

import com.mitocode.model.Config;

public interface IConfigDAO extends IDAO<Config>{

	Config leerParametro(String parametro) throws Exception;
	
}
