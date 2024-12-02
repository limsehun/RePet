package edu.kh.repet.hospital.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.repet.hospital.mapper.ApiMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequestMapping
@Transactional
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService{

	private final ApiMapper mapper;
	
	@Override
	public List<Map<String, Object>> selectHospitalList(String region) {
		return mapper.selectHospitalList(region);
	}
}
