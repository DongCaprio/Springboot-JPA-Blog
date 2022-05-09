package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

//밑에 어노테이션 3개는 그냥 세트라고 생각하자(이해 안되면 그냥 걸자)

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration // 빈 등록(IOC 관리)
@EnableWebSecurity  // 시큐리티 필터 추가 = 스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 해당 파일에서 하겠다
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //IOC가 됨, 리턴하는 값을 스프링이 관리한다
	public BCryptPasswordEncoder encodPWD() {
		//암호화(해쉬처리해줌)
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신 로그인해줘서 password를 가로채기를 하는데
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodPWD()); //괄호안의 값 => 위에있는 메소드
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			.csrf().disable() // csrf 토큰 비활성화(테스트시 걸어두는것이 좋음)
			.authorizeRequests()
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**","/dummy/**")
				.permitAll() //위에경로만 허용한다
				.anyRequest() //위에것이 아닌 다른 모든 요청은
				.authenticated() //인증된 사용자의 접근을 허용
			.and()
				.formLogin() //인증되지 않은 모든 폼은 로그인 페이지로 가고 그 주소는 밑에 줄이다
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
				.defaultSuccessUrl("/") //로그인이 성공하면 일로감
				/*.failureUrl("/fail")*/ //실패하면 일로감
				;
	}
}
