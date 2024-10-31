package edu.kh.repet.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.repet.board.dto.ReportComment;
import edu.kh.repet.board.mapper.ReportCommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReportCommentServiceImpl implements ReportCommentService {

	private final ReportCommentMapper mapper;
	
	@Override
	public int reportComment(ReportComment reportComment) {
		return mapper.insertReportComment(reportComment);
	}
}
