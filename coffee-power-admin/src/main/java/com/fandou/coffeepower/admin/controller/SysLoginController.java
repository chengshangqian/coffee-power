package com.fandou.coffeepower.admin.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fandou.coffeepower.admin.vo.LoginBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.fandou.coffeepower.admin.model.SysUser;
import com.fandou.coffeepower.admin.security.JwtAuthenticatioToken;
import com.fandou.coffeepower.admin.service.SysLoginLogService;
import com.fandou.coffeepower.admin.service.SysUserService;
import com.fandou.coffeepower.admin.util.IPUtils;
import com.fandou.coffeepower.admin.util.PasswordUtils;
import com.fandou.coffeepower.admin.util.SecurityUtils;
import com.fandou.coffeepower.common.utils.IOUtils;
import com.fandou.coffeepower.common.http.HttpResult;

/**
 * 登录控制器
 */
@RestController
public class SysLoginController {

	@Autowired
	private Producer producer;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysLoginLogService sysLoginLogService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		// 保存到验证码到 session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);	
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录接口
	 */
	@PostMapping(value = "/login")
	public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) {
		String username = loginBean.getAccount();
		String password = loginBean.getPassword();
		String captcha = loginBean.getCaptcha();

		// 从session中获取之前保存的验证码跟前台传来的验证码进行匹配
		HttpSession session = request.getSession();
		Object kaptcha = session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(kaptcha == null){
			return HttpResult.error("验证码已失效");
		}
		// 无论成功与否，先清除会话中的验证码，下次应刷新验证码在登录
		session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(!captcha.equals(kaptcha)){
			return HttpResult.error("验证码不正确");
		}

		// 用户信息
		SysUser user = sysUserService.findByName(username);
		// 账号不存在、密码错误
		if (user == null) {
			return HttpResult.error("账号不存在");
		}
		if (!PasswordUtils.matches(user.getSalt(), password, user.getPassword())) {
			return HttpResult.error("密码不正确");
		}
		// 账号锁定
		if (user.getStatus() == 0) {
			return HttpResult.error("账号已被锁定,请联系管理员");
		}
		// 系统登录认证
		JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
		// 记录登录日志
		sysLoginLogService.writeLoginLog(username, IPUtils.getIpAddr(request));
		return HttpResult.ok(token);
	}

}
