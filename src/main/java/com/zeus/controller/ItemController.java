package com.zeus.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zeus.service.ItemService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/item")
@MapperScan(basePackages = "com.zeus.mapper")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
}
