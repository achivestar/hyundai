package com.pe.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pe.domain.Memo;
import com.pe.repository.MemoRepository;


@Service
public class MemoService {

	@Autowired
	private MemoRepository memoRepository;
	
	@Transactional
	public Page<Memo> getMemoList(Pageable pageable) {
		return memoRepository.findAll(pageable);
	}
	
}
