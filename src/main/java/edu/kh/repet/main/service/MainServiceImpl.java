package edu.kh.repet.main.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.kh.repet.main.mapper.MainMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{
	
	private final MainMapper mapper;
	

}
