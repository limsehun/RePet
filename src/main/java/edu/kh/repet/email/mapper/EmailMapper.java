package edu.kh.repet.email.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {

	int updatePassword(String email, String newPassword);

}
