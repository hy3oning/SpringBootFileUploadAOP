package com.zeus.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zeus.domain.Item;
import com.zeus.service.ItemService;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequestMapping("/item")
@MapperScan(basePackages = "com.zeus.mapper")
public class ItemController {

	@Autowired
	private ItemService itemService;

	// properties 에서 upload.path에 저장된 값을 주입.
	@Value("${upload.path}")
	private String uploadPath;

	@GetMapping("/createForm")
	public String itemCreateForm(Model model) {
		log.info("createForm");
		return "item/createForm";
	}

	@PostMapping("/create")
	public String itemCreate(Item item, Model model) throws Exception {
		log.info("create" + item.toString());
		// 1. 파일 업로드한것을 가져올것
		MultipartFile file = item.getPicture();
		// 2. 파일 정보를 로그파일에 기록한다.
		log.info("originalName: " + file.getOriginalFilename());
		log.info("size: " + file.getSize());
		log.info("contentType: " + file.getContentType());
		// 3. 파일을 외장하드에 저장할것
		String createdFileName = uploadFile(file.getOriginalFilename(), file.getBytes());

		// 4. 새로운 파일을 item 도메인에 저장
		item.setUrl(createdFileName);
		// 5. 테이블에 상품정보를 저장
		int count = itemService.create(item);
		if (count > 0) {
			model.addAttribute("message", "상품등록이 되었습니다.");
			return "item/success";
		}
		model.addAttribute("message", "상품등록이 실패했습니다.");
		return "item/failed";

	}

	private String uploadFile(String originalName, byte[] fileData) throws Exception {
		// UUID == 절대 중복되지않는 문자열 생성
		UUID uid = UUID.randomUUID();
		// 8985f109-54c7-4fcf-b1b2-c54031962a05_FILENAME.jpg
		String createdFileName = uid.toString() + "_" + originalName;
		// new File("D:/upload","8985f109-54c7-4fcf-b1b2-c54031962a05_FILENAME.jpg")
		File target = new File(uploadPath, createdFileName);
		// byte[] fileData [원본 바이트 배열] 을 createFileName에 복사
		FileCopyUtils.copy(fileData, target);
		return createdFileName;
	}

	@GetMapping("/list")
	public String itemList(Model model) throws Exception {
		List<Item> itemList = itemService.list();
		model.addAttribute("itemList", itemList);
		return "item/list";
	}
	
	@GetMapping("/detail")
	public String getMethodName(@RequestParam String param) {
		return new String();
	}
	

}
