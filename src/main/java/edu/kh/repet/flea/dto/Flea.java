package edu.kh.repet.flea.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
//@Builder
public class Flea {
	
	private int		boardNo;
	private String	boardTitle;
	private String	boardContent;
	private String  memberNickname;
	private int		memberNo;
	private int		chatRoomCount;
	private String	boardWriteDate;
	private String	boardUpdateDate;
	private String	address;
	private String	thumbnail;
	private String	profileImg;
	private int 	price;
}
