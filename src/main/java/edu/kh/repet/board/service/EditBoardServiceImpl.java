package edu.kh.repet.board.service;

import org.springframework.stereotype.Service;

import edu.kh.repet.board.mapper.EditBoardMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditBoardServiceImpl implements EditBoardService {

	private final EditBoardMapper mapper;
	
}
