package com.zeus.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
		log.info("itemList");
		List<Item> itemList = itemService.list();
		model.addAttribute("itemList", itemList);
		return "item/list";
	}

	@GetMapping("/detail")
	public String itemDetail(Item i, Model model) throws Exception {
		log.info("detail");
		Item item = itemService.read(i);
		model.addAttribute("item", item);
		return "item/detail";
	}

	// 화면 요청이아닌 데이터를 보내줄것을 요청.
	@ResponseBody
	@GetMapping("/display")
	public ResponseEntity<byte[]> itemDisplay(Item item) throws Exception {
		log.info("itemDisplay: ");
		// 파일을 읽기위한 스트림
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		String url = itemService.getPicture(item);
		log.info("FILE URL: " + url);
		try {
			// b2ae77ef-1633-43d9-a65c-1ed4edfd66fd_스크린샷(1).png + 1 substring
			// 파일의 확장자를 가져옴 String formatName = "png"
			String formatName = url.substring(url.lastIndexOf(".") + 1);
			// 확장자가 jpg라면 MediaType.IMAGE_JPEG
			MediaType mType = getMediaType(formatName);
			// 클라이언트 <-> 서버(header, body)
			HttpHeaders headers = new HttpHeaders();
			// 이미지파일을 inputStream 으로 가져옴
			in = new FileInputStream(uploadPath + File.separator + url);
			// 이미지파일타입이 null 아니라면, headers 에 이미지타입 저장
			if (mType != null) {
				headers.setContentType(mType);
			}
			// IOUtils.toByteArray(in) : inputStream 저장된 파일을 byte[] 로 변환
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	@GetMapping(value = "/updateForm")
	public String itemUpdateForm(Item i, Model model) throws Exception {
		log.info("update item" + i.toString());
		Item item = this.itemService.read(i);
		model.addAttribute("item", item);
		return "item/updateForm";
	}

	@PostMapping("/update")
	public String itemUpdate(Item item, Model model) throws Exception {
		log.info("/update item" + item.toString());

		MultipartFile file = item.getPicture();
		String oldUrl = null; // if문 밖에서 미리 선언 (나중에 삭제할 때 사용)

		// 1. 새로운 파일이 업로드되었는지 확인
		if (file != null && file.getSize() > 0) {
			// 기존 파일 정보를 가져옴 (삭제를 위해)
			Item oldItem = itemService.read(item);
			oldUrl = oldItem.getUrl();

			// 새로운 파일 정보 출력 및 저장
			log.info("New OriginalName: " + file.getOriginalFilename());
			log.info("New Size: " + file.getSize());

			String createdFileName = uploadFile(file.getOriginalFilename(), file.getBytes());
			item.setUrl(createdFileName); // 새로운 파일명으로 업데이트
		}

		// 2. DB 업데이트 실행
		int count = itemService.update(item);

		if (count > 0) {
			// DB 수정 성공 시에만 이전 이미지 파일을 실제로 삭제
			if (oldUrl != null) {
				deleteFile(oldUrl);
			}
			model.addAttribute("message", "상품수정 성공");
			return "item/success";
		}

		model.addAttribute("message", "상품수정 실패");
		return "item/failed";
	}

	// 외부저장소 자료업로드 파일명생성후 저장
	// c:/upload/"../window/system.ini" 디렉토리 탈출공격(path tarversal)
	private boolean deleteFile(String fileName) throws Exception {
		if (fileName.contains("..")) {
			throw new IllegalArgumentException("잘못된 경로 입니다.");
		}
		File file = new File(uploadPath, fileName);
		return (file.exists() == true) ? (file.delete()) : (false);
	}

	private MediaType getMediaType(String form) {
		String formatName = form.toUpperCase();
		if (formatName != null) {
			if (formatName.equals("JPG")) {
				return MediaType.IMAGE_JPEG;
			}
			if (formatName.equals("GIF")) {
				return MediaType.IMAGE_GIF;
			}
			if (formatName.equals("PNG")) {
				return MediaType.IMAGE_PNG;
			}
		}
		return null;
	}

}
