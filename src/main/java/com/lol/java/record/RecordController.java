package com.lol.java.record;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import org.python.jline.internal.InputStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// 전적 페이지
@Controller
@RequestMapping("/record")
public class RecordController {

	@Autowired
	private RecordService recordService;
	
	// 전적 페이지 불러오기
	@RequestMapping("/record.do")
	public void record(Model model, RecordVO recordVO) {
		model.addAttribute("record", recordService.record_info(recordVO));
		model.addAttribute("score", recordService.record_score(recordVO));
	}
	
	// 전적 페이지 불러오기
	@RequestMapping("/record_update.do")
	public String record_update(RecordVO recordVO) throws IOException, InterruptedException {		
		String command = "C:\\Users\\grood\\anaconda3\\envs\\pythonProject\\python.exe";  // 명령어
    	String arg = "C:\\Users\\grood\\PycharmProjects\\pythonProject\\crawling.py"; // 인자
    	ProcessBuilder builder = new ProcessBuilder(command, arg, recordVO.getSummoner_id(), recordVO.getGameid(), String.valueOf(recordVO.getUser_no()));
    	builder.redirectOutput(Redirect.INHERIT);
    	builder.redirectError(Redirect.INHERIT);    	
    	builder.redirectErrorStream(true);
    	Process process = builder.start();
    	int exitVal = process.waitFor();  // 자식 프로세스가 종료될 때까지 기다림
    	BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream())); // 서브 프로세스가 출력하는 내용을 받기 위해
    	String line;
    	if(exitVal != 0) {
    	  // 비정상 종료
    	  System.out.println("서브 프로세스가 비정상 종료되었다.");
    	}
    	String encodedSum = URLEncoder.encode(recordVO.getSummoner_id(), "UTF-8");
    	return "redirect:/record/record.do?summoner_id="+encodedSum;
	}
	
	// 전적 더보기 버튼
	@ResponseBody
	@RequestMapping("/record_more.do")
	public Object record_more(int begin_num, int user_no) {
		
		HashMap<String,Object> record = new HashMap<String, Object>();
		record.put("user_no", user_no);
		record.put("begin_num", begin_num);
		List<RecordVO> recordVO = recordService.record_more(record);
		return recordVO;
		
	}
	
	// 시각화를 위한 데이터 가져오기
	@ResponseBody
	@RequestMapping("/record_chart.do")
	public HashMap<String, List<RecordVO>> record_chart(int user_no) {
		List<RecordVO> recordVO1 = recordService.record_chart(user_no);
		List<RecordVO> recordVO2 = recordService.record_champion_rate(user_no);
		List<RecordVO> recordVO3 = recordService.record_lane_rate(user_no);
		
		HashMap<String, List<RecordVO>> chart = new HashMap<String, List<RecordVO>>();
		chart.put("chart", recordVO1);
		chart.put("champion_rate", recordVO2);
		chart.put("lane_rate", recordVO3);
		return chart;
	}
	
}
