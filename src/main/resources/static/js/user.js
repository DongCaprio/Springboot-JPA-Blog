let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});
	},
	save: function() {
		//alert("user의 save함수 호출")
		let data = {
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};
		//console.log(data);
		
		//ajax호출시 default가 비동기 호출
		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 
		$.ajax({
			//회원가입 수행 요청
			type:"POST",
			url:"/auth/joinProc",
			data: JSON.stringify(data),
			contentType:"application/json; charset=utf-8", //body데이터가 어떤 타입인지(MIME)
			dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 객체로 변환해줌
		}).done(function(resp){ 
			//성공시
			alert("회원가입이 완료되었습니다.")
			//console.log(resp);
			location.href="/";
		}).fail(function(error){
			//실패시
			alert(JSON.stringify(error)); 
		}); 
	},
}

index.init();