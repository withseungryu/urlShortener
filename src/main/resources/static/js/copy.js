function copy() {
    var obj = document.getElementById("url2");
    obj.select(); //인풋 컨트롤의 내용 전체 선택
    document.execCommand("copy"); //복사
    obj.setSelectionRange(0, 0); //선택영역 초기화
}
