package com.cos.util;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import com.cos.model.Board;

public class Utils {

	// 미리보기 내용을 세팅하기
	public static void setPreviewContent(List<Board> boards) {

		for (Board board : boards) {
			Document doc = Jsoup.parse(board.getContent());
			// img 태그를 찾아서 배열로 만들어준다.

			Elements ets = doc.select("img");

			// for문을 돌면서 remove 해줄거다.
			if (ets != null) {
				// ets를 돌면서 et를 하나씩 찾아내는거
				for (Element et : ets) {
					// 지우는 것
					et.remove();
				}

			}
			board.setContent(doc.toString());
		}
		// 어짜피 BoardListAction에서 주소를 넘기는 것이기 때문에 return 해줄 필요 없다.
		// return boards;
	}

	// 미리보기 이미지 세팅하기
	public static void setPreviewImg(List<Board> boards) {
		for (Board board : boards) {
			if (board.getContent() != null) {
				Document doc = Jsoup.parse(board.getContent());
				Elements etyoutube = doc.select("a");
				Element et = doc.selectFirst("img");
				String thumbnail = "";
				if (etyoutube != null) {
					for (Element element : etyoutube) {
						String href = element.attr("href");
						String value = element.text();
						if (href.contains("https://www.youtube.com/watch") && !value.equals("")) {
							String video[] = href.split("=");
							String v = video[1];
							thumbnail = "http://i.ytimg.com/vi/" + v + "/0.jpg";
							board.setPreviewImg(thumbnail);
							break;
						}
					}
				}
				if (thumbnail.equals("")) {
					if (et != null) {
						String previewImg = et.attr("src");// 이미지 소스 찾기
						board.setPreviewImg(previewImg);
					} else {
						board.setPreviewImg("/blog/img/home-blog/blog-1.jpg");
					}
				}
			} else {
				board.setPreviewImg("/blog/img/home-blog/blog-1.jpg");
			}
		}

	}

	// 유투브 미리보기 세팅하기
	public static void setPreviewYoutube(Board board) {

		Document doc = Jsoup.parse(board.getContent());
		Elements ets = doc.select("a");

		if (ets != null) {
			for (Element et : ets) {
				String href = et.attr("href");
				// &&뒤의 태그는 썸머노트 a태그 중복 버그 때문에 쓴다!!
				if (href.contains("https://www.youtube.com/watch") && !et.text().equals("")) {
					String video[] = href.split("=");
					// "=" 뒤의 값이 필요하므로
					String v = video[1];
					String iframe = "<iframe src ='https://www.youtube.com/embed/" + v
							+ "' width = '600px' height = '350px' allowfullscreen></iframe>";
					et.after(iframe);
				}
			}
			board.setContent(doc.toString());// board 완성
		}
	}

	@Test
	public void previewYoutubeTest() {
		String content = "<a href='https://www.youtube.com/watch?v=BzYnNdJhZQw'>https://www.youtube.com/watch?v=BzYnNdJhZQw</a>";
		Document doc = Jsoup.parse(content);
		Elements ets = doc.select("a");

		if (ets != null) {
			for (Element et : ets) {
				String href = et.attr("href");
				String value = et.text();
				System.out.println("value : " + value);
				System.out.println(href);
				if (href.contains("https://www.youtube.com/watch")) {
					String video[] = href.split("=");
					// "=" 뒤의 값이 필요하므로
					String v = video[1];
					System.out.println(v);
					String iframe = "<iframe src ='https://www.youtube.com/embed/" + v
							+ "' width = '600px' height = '350px' allowfullscreen></iframe>";
					System.out.println(iframe);
					et.after(iframe);
					System.out.println(doc);
				}

			}
		}
	}

	public void previewContentTest() {
		String content = "내용";

		Document doc = Jsoup.parse(content);
		System.out.println(doc);

		// img 태그를 찾아서 배열로 만들어준다.
		Elements ets = doc.select("img");
		System.out.println(ets.get(0));
		System.out.println(ets.get(1));

		// for문을 돌면서 remove 해줄거다.
		if (ets != null) {
			// ets를 돌면서 et를 하나씩 찾아내는거
			for (Element et : ets) {
				// 지우는 것
				et.remove();
			}
			System.out.println(doc);
		}
	}
}
