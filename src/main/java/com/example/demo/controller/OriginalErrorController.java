package com.example.demo.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class OriginalErrorController implements ErrorController {

	/**
	   * HTML レスポンス用の ModelAndView オブジェクトを返す。
	   *
	   * @param req リクエスト情報
	   * @param mav レスポンス情報
	   * @return HTML レスポンス用の ModelAndView オブジェクト
	   */
	@GetMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView myErrorHtml(HttpServletRequest req, ModelAndView mav) {

		// エラー情報を取得
		Map<String, Object> attr = getErrorAttributes(req);

		// HTTP ステータスを決める
		HttpStatus status = getHttpStatus(req);

		// HTTP ステータスをセットする
		mav.setStatus(status);
		// ビュー名にerror.htmlをセット
		mav.setViewName("error2");

		mav.addObject("timestamp", new Date());
		mav.addObject("status", status.value());
		mav.addObject("error", attr.get("error"));
		mav.addObject("path", req.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));

		// 出力したい情報をセットする
		System.out.println("status : " + status.value());
		System.out.println("timestamp : " + attr.get("timestamp"));
		System.out.println("error : " + attr.get("error"));
		System.out.println("exception : " + attr.get("exception"));
		System.out.println("message : " + attr.get("message"));
		System.out.println("errors : " + attr.get("errors"));
		System.out.println("trace : " + attr.get("trace"));
		System.out.println("path : " + attr.get("path"));

		// ステータスに応じて処理
		if (status == HttpStatus.NOT_FOUND) {
			// 404 Not Found
			mav.addObject("message", "ページが見つかりません。");
		} else {
			// 404 以外は500 Internal Server Error とする
			mav.addObject("message", "システムエラーが発生しました。システム管理者にお問い合わせ下さい。");
		}

		return mav;
	}

	/**
	  * JSON レスポンス用の エラー情報を抽出する。
	  *
	  * @param req リクエスト情報
	  * @return エラー情報
	  */
	private Map<String, Object> getErrorAttributes(HttpServletRequest req) {
		// DefaultErrorAttributes クラスで詳細なエラー情報を取得する
		ServletWebRequest swr = new ServletWebRequest(req);
		DefaultErrorAttributes dea = new DefaultErrorAttributes();
		ErrorAttributeOptions eao = ErrorAttributeOptions.of(
				ErrorAttributeOptions.Include.BINDING_ERRORS,
				ErrorAttributeOptions.Include.EXCEPTION,
				ErrorAttributeOptions.Include.MESSAGE,
				ErrorAttributeOptions.Include.STACK_TRACE);
		return dea.getErrorAttributes(swr, eao);
	}

	/**
	 * レスポンス用の HTTP ステータスを決める。
	 *
	 * @param req リクエスト情報
	 * @return レスポンス用 HTTP ステータス
	 */
	private static HttpStatus getHttpStatus(HttpServletRequest req) {
		// HTTP ステータスを決める
		// ここでは 404 以外は全部 500 にする
		Object statusCode = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		HttpStatus status = HttpStatus.resolve((int) statusCode);
		if (statusCode != null && statusCode.toString().equals("404")) {
			status = HttpStatus.NOT_FOUND;
		}
		return status;
	}

}
