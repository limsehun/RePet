package edu.kh.repet.manager.boardmanager.service;

import org.springframework.stereotype.Service;

import edu.kh.repet.manager.mapper.ManagerMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardManagerServiceImpl implements BoardManagerService{
	
	private final ManagerMapper mapper;
	

}
