package main.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.service.BoardService;
import main.service.BoardVO;

@Controller
public class BoardController {

	@Resource(name="boardService")
	private BoardService boardService;
	
	@RequestMapping("/boardWrite.do")
	public String boardWrite() {
		
		return "board/boardWrite";
	}
	
	@RequestMapping("/boardWriteSave.do")
	@ResponseBody
	public String insertNBoard(BoardVO vo) throws Exception{
		// result=null;
		String result=boardService.insertNBoard(vo);
		String msg="";
		if(result==null) msg="ok";
		else msg="fail";
		return msg;
	}
	
	@RequestMapping("/boardList.do")
	public String selectNBoardList(BoardVO vo,ModelMap model) throws Exception{
		
		int unit=5;
		// 총 데이터 개수
		int total=boardService.selectNBoardTotal(vo);
		
		// 12/10->ceil(1.2) ->Integer(2.0)->2
		int totalPage=(int)Math.ceil((double)total/unit);
		
		int viewPage=vo.getViewPage();
		
		if(viewPage>totalPage || viewPage<1) {
			viewPage=1;
		}
		// 1->1,10 // 2->11,20 //3->21,30
		// startIndex : (1-1)*10+1 ->1
		// startIndex : (2-1)*10+1 ->11
		int startIndex=(viewPage-1)*unit+1;
		int endIndex=startIndex+(unit-1);
		
		// total ->34(4page)
		// 1->34~25,2->24~15, 3->14~5,4->4~1
		
		//int p1=total-0;
		//int p2=total-10;
		//int p3=total-20;
		//int p4=total-30;
		
		int startRowNo=total-(viewPage-1)*unit;
		
		vo.setStartIndex(startIndex);
		vo.setEndIndex(endIndex);
		
		List<?> list=boardService.selectNBoardList(vo);
		System.out.println("list : "+list);
		
		model.addAttribute("rowNumber",startRowNo);
		model.addAttribute("total",total);
		model.addAttribute("totalPage",totalPage);
		model.addAttribute("resultList",list);
		
		return "board/boardList";
	}
	
	@RequestMapping("/boardDetail.do")
	public String selectNBoardDetail(BoardVO vo,ModelMap model) throws Exception{
		
		/*
		 * 조회수 증가
		 */
		boardService.updateNBoardHits(vo.getUnq());
		
		/*
		 * 상세보기
		 */
		BoardVO boardVO=boardService.selectNBoardDetail(vo.getUnq());
		
		String content=boardVO.getContent();
		boardVO.setContent(content.replace("\n", "<br>"));
		
		model.addAttribute("boardVO",boardVO);
		
		return "board/boardDetail";
	}
	
	@RequestMapping("/boardModifyWrite.do")
	public String selectNBoardModifyWrite(BoardVO vo,ModelMap model) throws Exception{
		
		BoardVO boardVO=boardService.selectNBoardDetail(vo.getUnq());
		model.addAttribute("boardVO", boardVO);
		
		return "board/boardModifyWrite";
	}
	
	@RequestMapping("/boardModifySave.do")
	@ResponseBody
	public String updateNBoard(BoardVO vo) throws Exception {
		
		
		int result=0;
		int count=boardService.selectNBoardPass(vo);
		if(count==1) {
			result=boardService.updateNBoard(vo);
		
		}else {
			result=-1;
		}
		
		
		return result+"";
	}
	
	@RequestMapping("/passWrite.do")
	public String passWrite(int unq,ModelMap model) {
		model.addAttribute("unq", unq);
		return "board/passWrite";
	}
	
	@RequestMapping("/boardDelete.do")
	@ResponseBody
	public String deleteBoard(BoardVO vo) throws Exception{
		int result=0;
		/*
		 * 암호 일치 검사
		 */
		int count=boardService.selectNBoardPass(vo);
		
		if(count==1) {
		result=boardService.deleteNBoard(vo);
		}else if(count==0) {
			result=-1;
		}
		return result+"";
	}
}
