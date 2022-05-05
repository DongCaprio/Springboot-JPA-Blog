package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//밑에 어노테이션 3개는 그냥 세트라고 생각하자(이해 안되면 그냥 걸자)

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration // 빈 등록(IOC 관리)
@EnableWebSecurity  // 시큐리티 필터 추가 = 스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 해당 파일에서 하겠다
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean //IOC가 됨, 리턴하는 값을 스프링이 관리한다
	public BCryptPasswordEncoder encodPWD() {
		//암호화(해쉬처리해줌)
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			.csrf().disable() // csrf 토큰 비활성화(테스트시 걸어두는것이 좋음)
			.authorizeRequests()
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
				.permitAll() //위에경로만 허용한다
				.anyRequest() //위에것이 아닌 다른 모든 요청은
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				; //인증이 필요하다
	}
}
