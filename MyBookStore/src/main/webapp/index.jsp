<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="refresh" content="5;url=views/nav.html">
    <title>自动跳转页面</title>
    <style>
    	h2 {
    		padding: 8px;
    		margin: 10 auto;
    	}
    	span {
    		color: red;
    	}
    </style>
</head>
<body>
<h2>页面将在 <span id="countdown">5</span> 秒后转跳至登录界面</h2>
<h2>如果转跳未发生，请点击<a href="login">这里</a></h2>
<script type="text/javascript">
//定义倒计时时间（秒）
let countdownTime = 5;

// 获取倒计时显示元素
const countdownElement = document.getElementById('countdown');

// 更新倒计时显示
function updateCountdown() {
    countdownElement.textContent = countdownTime;
}

// 开始倒计时
function startCountdown() {
    const countdownInterval = setInterval(() => {
        if (countdownTime > 0) {
            countdownTime--;
            updateCountdown();
        }
    }, 1000);
}

// 初始化倒计时
updateCountdown();
startCountdown();
</script>
</body>
</html>
