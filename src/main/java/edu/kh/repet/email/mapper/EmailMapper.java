package edu.kh.repet.email.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailMapper {

	int updatePw(@Param("memberEmail") String email, @Param("encPw") String encPw);

//	int updateDelFl(@Param("memberEmail") String email, 
//									@Param("memberDelFl") String memberDelFl);

}
