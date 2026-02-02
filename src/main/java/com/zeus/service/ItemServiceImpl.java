package com.zeus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeus.domain.Item;
import com.zeus.mapper.ItemMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;

	@Override
	@Transactional
	public int create(Item item) throws Exception {
		return itemMapper.create(item);
	}

	@Override
	@Transactional
	public int update(Item item) throws Exception {
		return itemMapper.update(item);
	}

	@Override
	@Transactional
	public int delete(Item item) throws Exception {
		return itemMapper.delete(item);
	}

	@Override
	@Transactional(readOnly = true)
	public Item read(Item item) throws Exception {
		log.info("ItemServiceImpl read = detail입니다.");
		return itemMapper.read(item);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Item> list() throws Exception {
		log.info("ItemServiceImpl list = LIST 입니다.");

		return itemMapper.list();
	}

	@Override
	@Transactional
	public String getPicture(Item item) throws Exception {
		return itemMapper.getPicture(item);
	}

}
