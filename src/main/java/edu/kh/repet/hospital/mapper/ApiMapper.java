package edu.kh.repet.hospital.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiMapper {

	List<Map<String, Object>> selectHospitalList(String region);

}
