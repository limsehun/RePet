package edu.kh.repet.hospital.service;

import java.util.List;
import java.util.Map;

public interface ApiService {

	List<Map<String, Object>> selectHospitalList(String string);

}
