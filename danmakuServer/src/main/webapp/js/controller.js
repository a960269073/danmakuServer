//创建舞台
var canvas;

//调整大小
$(document).ready(
    function () {
        onResize();
        WebSocketInit();
        canvas = oCanvas.create({
            canvas: "#container",
            fps: 120,
            width: document.body.offsetWidth,
            height: document.body.offsetHeight
        });
    }
);


//触发修改窗口方法
function onResize() {
    $("#container").attr("height", document.body.offsetHeight);
    $("#container").attr("width", document.body.offsetWidth);

    // t.height = document.body.offsetHeight;
    // t.width = document.body.offsetWidth;
}


function danmaku(danmaku) {
    onResize();

    var positionY = Math.round(Math.random() * canvas.height / 20) * 20;


    var text = canvas.display.text({
        x: 0,
        y: positionY,
        origin: {x: "center", y: "top"},
        font: "20px sans-serif",
        text: danmaku,
        fill: "#fff"
    });

    canvas.addChild(text);

    text.animate({
        x: canvas.width,
        y: positionY,
        height: 300
    }, 5000, "linear");

    var t = setTimeout(function () {
        console.log("1");
        if (text.x >= canvas.width) {
            canvas.removeChild(text);
        }
    }, 5500);
}

function WebSocketInit() {
    if ("WebSocket" in window) {
        console.log("您的浏览器支持 WebSocket!");

        // 打开一个 web socket
        var ws = new WebSocket("ws://localhost:8081/danmakuServer/service/webSocketServer");

        ws.onopen = function () {
            // Web Socket 已连接上，使用 send() 方法发送数据
            // ws.send("发送数据");
            // alert("数据发送中...");
        };

        ws.onmessage = function (evt) {
            var received_msg = evt.data;
            // alert("数据已接收...");
            console.log(evt.data);

            var data = JSON.parse(evt.data);
            console.log(data.danmaku);

            danmaku(data.danmaku);
        };

        ws.onclose = function () {
            // 关闭 websocket
            // alert("连接已关闭...");
        };
    } else {
        // 浏览器不支持 WebSocket
        console.log("您的浏览器不支持 WebSocket!");
    }
}