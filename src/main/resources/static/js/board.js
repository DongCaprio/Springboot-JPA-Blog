let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});
	},
	save: function() {
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
		};

		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", //body데이터가 어떤 타입인지(MIME)
			dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 객체로 변환해줌
		}).done(function(resp) {
			alert("글쓰기가 완료되었습니다.")
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
}
index.init();