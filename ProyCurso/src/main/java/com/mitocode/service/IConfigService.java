package com.mitocode.service;

import com.mitocode.model.Config;

public interface IConfigService extends IService<Config>{

	Config leerParametro(String parametro) throws Exception;
}
