package edu.kh.repet.adopt.service;

import java.io.IOException;
import java.util.Map;



public interface AdoptService {

	Map<String, Object> selectAdoptList(String urlStr) throws IOException;

}
