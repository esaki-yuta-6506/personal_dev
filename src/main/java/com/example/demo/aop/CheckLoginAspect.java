package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Mode;
import com.example.demo.model.Account;
import com.example.demo.repository.ModeRepository;

@Aspect
@Component
public class CheckLoginAspect {

	@Autowired
	Account account;
	
	@Autowired
	ModeRepository modeRepository;

	@Before("execution(* com.example.demo.controller.*Controller.*(..))")
	public void writeLog(JoinPoint jp) {
		if (account == null || account.getName() == null || account.getName().length() == 0) {
			System.out.print("ゲスト : ");
		} else {
			Mode mode = modeRepository.findOneById(account.getModeId());
			System.out.print(account.getName() + " : ");
			System.out.print(mode.getName() + " : ");
		}
		System.out.println(jp.getSignature());
	}

	//未ログインの場合ログインページにリダイレクト
	@Around("execution(* com.example.demo.controller.ItemController.*(..))"
			+ " || execution(* com.example.demo.controller.CartController.*(..))"
			+ " || execution(* com.example.demo.controller.OrderController.*(..))")
	public Object checkLogin(ProceedingJoinPoint jp) throws Throwable {

		if (account == null || account.getName() == null || account.getName().length() == 0) {
			System.err.println("ログインしていません!");
			return "redirect:/login?error=notLoggedIn";
		}
		//Controller内のメソッドの実行
		return jp.proceed();
	}

	//ショップアカウントが不正の場合はitemsに返す
	@Around("execution(* com.example.demo.controller.shop.ShopController.*(..))")
	public Object checkShop(ProceedingJoinPoint jp) throws Throwable {

		if (account == null || account.getName() == null || account.getName().length() == 0 || account.getModeId() > 2) {
			System.err.println("ショップに不正なアクセスです!");
			return "redirect:/items";
		}

		return jp.proceed();
	}

	//管理者アカウントが不正の場合はitemsに返す
	@Around("execution(* com.example.demo.controller.admin.AdminAccountController.*(..))"
			+ " || execution(* com.example.demo.controller.admin.AdminShopController.*(..))")
	public Object checkAdmin(ProceedingJoinPoint jp) throws Throwable {

		if (account == null || account.getName() == null || account.getName().length() == 0 || account.getModeId() != 1) {
			System.err.println("管理者メニューに不正なアクセスです!");
			return "redirect:/items";
		}

		return jp.proceed();
	}
}
