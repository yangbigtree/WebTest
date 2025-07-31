document.getElementById('signForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 阻止表单默认提交行为

    // 获取输入值
    const username = document.getElementById('account').value;
    const password = document.getElementById('password').value;

	const error = document.getElementById('error');
	error.textContent = '';
	error.style.display = 'none';
	
    let isValid = true;

    // 验证用户名
	const usernameRegex = /^[A-Za-z0-9]{8,16}$/;
    if (username.trim() === '') {
        error.textContent = '用户名不能为空';
		error.style.display = 'block';
        isValid = false;
    } else if (!usernameRegex.test(username)) {
        error.textContent = '用户名必须是8~16个英文或数字';
		error.style.display = 'block';
        isValid = false;
    }

    // 验证密码
	const passwordRegex = new RegExp("^(?:(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)"          // 小写+大写+数字
	+ "|(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_+\\-={}\\[\\]|;:\"'<>,.?/])"  // 小写+大写+特殊
	+ "|(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*()_+\\-={}\\[\\]|;:\"'<>,.?/])"    // 小写+数字+特殊
	+ "|(?=.*[A-Z])(?=.*\\d)(?=.*[~!@#$%^&*()_+\\-={}\\[\\]|;:\"'<>,.?/]))"   // 大写+数字+特殊
	+ "[A-Za-z\\d~!@#$%^&*()_+\\-={}\\[\\]|;:\"'<>,.?/]{8,32}$");
    if (password.trim() === '') {
        error.textContent = '密码不能为空';
		error.style.display = 'block';
        isValid = false;
    } else if (!passwordRegex.test(password)) {
        error.textContent = '密码8~32位至少包含大写、小写、数字或字符中三种类型的字符';
		error.style.display = 'block';
        isValid = false;
    } else if (password !== password2) {
	    error.textContent = '两次输入的密码不一致';
		error.style.display = 'block';
	    isValid = false;
	}

    // 如果所有验证通过，则提交表单
    if (isValid) {
        document.getElementById('registrationForm').submit();
    }
});