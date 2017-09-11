package com.stelinno.cloud.bluemix;

import java.util.List;

public interface DocumentPersistenceService {
	public void store(String json);
	public List<FootballMatch> find(String query);
}
