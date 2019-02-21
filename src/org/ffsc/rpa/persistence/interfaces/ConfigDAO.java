package org.ffsc.rpa.persistence.interfaces;

import org.ffsc.rpa.domain.Config;

public interface ConfigDAO {

	public boolean save(Config cfg);

	public Config get();
}