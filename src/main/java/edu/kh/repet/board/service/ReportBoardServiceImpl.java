package edu.kh.repet.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.repet.board.dto.ReportBoard;
import edu.kh.repet.board.mapper.ReportBoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReportBoardServiceImpl implements ReportBoardService {


	private final ReportBoardMapper mapper;

	// 게시글 신고
	@Override
	public int reportInsert(ReportBoard reportBoard) {
		return mapper.reportInsert(reportBoard);
	}
	
}
