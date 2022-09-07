package site.metacoding.red.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.MainDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {

	private final HttpSession session; // 스프링이 서버시작시에 IoC 컨테이너에 보관함.
	private final BoardsDao boardsDao;
	// @PostMapping("/boards/{id}/delete")
	// @PostMapping("/boards/{id}/update")

	@PostMapping("/boards")
	public String writeBoards(WriteDto writeDto) {
		// 1번 세션에 접근해서 세션 값을 확인한다. 그 때 Users로 다운캐스팅하고 키 값은 principal로 한다.

		// 2번 principal이 null인지 확인하고 null이면 loginForm을 redirect해준다.

		// 3번 BoardsDao에 접근해서 insert 메서드를 호출한다.
		// 조건 : dto를 entity로 변환해서 인수로 담아준다.
		// 조건 : entity에는 세션의 principal에 getId가 필요하다.
		Users principal = (Users) session.getAttribute("principal");// 1번

		if (principal == null) {// 2번
			return "redirect:/loginForm";
		}

		boardsDao.insert(writeDto.toEntity(principal.getId()));// 3번
		return "redirect:/";
	}

	// http://localhost:8000/ => null 값이 받아지는데 null 값일때도 첫 페이지가 나올 수 있게 해야함.
	// http://localhost:8000/?page=0
	@GetMapping({ "/", "/boards" })
	public String getBoardList(Model model, Integer page) {// 0->0, 1->10, 2->20 한번에 뜨는 게시물개수를 10개로 정했기때문.
		if (page == null) page = 0;
		int startNum = page * 3;
		List<MainDto> boardsList = boardsDao.findAll(startNum);
		PagingDto paging = boardsDao.paging(page); 
		
		final int blockCount = 5;
		
		int currentBlock = page / blockCount;
		int startPageNum =  (currentBlock*blockCount) + 1;
		int lastPageNum =  startPageNum + (blockCount -1);
		
		if(paging.getTotalPage()< lastPageNum) {
			lastPageNum = paging.getTotalPage();
		}
		
		paging.setBlockCount(blockCount);
		paging.setCurrentBlock(currentBlock);
		paging.setStartPageNum(startPageNum);
		paging.setLastPageNum(lastPageNum);
		
		model.addAttribute("boardsList", boardsList);
		model.addAttribute("paging", paging);
		return "boards/main";
	}

	@GetMapping("/boards/{id}")
	public String getBoardDetail(@PathVariable Integer id, Model model) {
		model.addAttribute("boards", boardsDao.findById(id));
		return "boards/detail";
	}

	@GetMapping("/boards/writeForm")
	public String writeForm() {
		Users principal = (Users) session.getAttribute("principal");
		if (principal == null) {
			return "redirect:/loginForm";
		}
		return "boards/writeForm";

	}
}
