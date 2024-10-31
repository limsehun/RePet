package edu.kh.repet.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReportComment {

	private int memberNo;
	private int commentNo;
	private String commentContent;
	private String reportDate;
	private int progressCode;
	private int reportCategory;
	
}
