package com.example.sample;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class GameController {

	@Autowired // 自分でsessionインスタンスを作成する必要がなくなる
	HttpSession session;
	
	@GetMapping("/")
	public String index(GameForm gameForm) {
		session.invalidate();
		// 答えを作成しSessionに格納
		int answer = (int) (Math.random() * 100) + 1;
		session.setAttribute("answer", answer);
		System.out.println("answer = " + answer);
		
		return "game";
	}
	
	@PostMapping("/challenge")
	public String challenge(@Valid GameForm gameForm, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			return "game";
		}
		
		// ユーザーの回答を取得
		int number = gameForm.getNumber();
		
//		model.addAttribute("number", gameForm.getNumber());
//		System.out.println("number = " + gameForm.getNumber());
		
		// セッションから答えを取得
		int answer = (Integer) session.getAttribute("answer");
		
		// ユーザーの回答履歴を取得
		@SuppressWarnings("unchecked")
		List<History> histories = (List<History>) session.getAttribute("histories");
		if (histories == null) {
			histories = new ArrayList<History>();
			// 回答履歴をsessionに格納
			session.setAttribute("histories", histories);
		}
		
		// 判定⇒回答履歴追加
		if (answer < number) {
			histories.add(new History(histories.size() + 1, number, "More Smaller"));
		} else if (answer == number) {
			histories.add(new History(histories.size() + 1, number, "Just!!!"));
		} else {
			histories.add(new History(histories.size() + 1, number, "More Higher"));
		}
		
//		for (History h:histories) {
//			System.out.println(h.getCount() + " " + h.getYourAnswer() + " " + h.getResult());
//		}
		
		model.addAttribute("histories", histories);
				
		return "game";
	}
}
