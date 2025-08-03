document.getElementById('signForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 阻止表单默认提交行为

    // 获取输入值
    const username = document.getElementById('account').value;
    const password = document.getElementById('password').value;
    const password2 = document.getElementById('password2').value;
    const phone = document.getElementById('phone').value;
    const email = document.getElementById('email').value;

    let isValid = true;

    // 验证用户名
    const usernameRegex = /^[A-Za-z0-9]{8,16}$/;
    if (username.trim() === '') {
        usernameError.textContent = '用户名不能为空';
        isValid = false;
    } else if (!usernameRegex.test(username)) {
        usernameError.textContent = '用户名必须是8~16个英文或数字';
        isValid = false;
    }

    // 验证密码
    const passwordRegex = new RegExp("^(?:(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)"          // 小写+大写+数字
    + "|(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_+\\-={}\\[\\]|;:\"'<>,.?/])"  // 小写+大写+特殊
    + "|(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*()_+\\-={}\\[\\]|;:\"'<>,.?/])"    // 小写+数字+特殊
    + "|(?=.*[A-Z])(?=.*\\d)(?=.*[~!@#$%^&*()_+\\-={}\\[\\]|;:\"'<>,.?/]))"   // 大写+数字+特殊
    + "[A-Za-z\\d~!@#$%^&*()_+\\-={}\\[\\]|;:\"'<>,.?/]{8,32}$");
    if (password.trim() === '') {
        passwordError.textContent = '密码不能为空';
        isValid = false;
    } else if (!passwordRegex.test(password)) {
        passwordError.textContent = '密码8~32位至少包含大写、小写、数字或字符中三种类型的字符';
        isValid = false;
    } else if (password !== password2) {
        passwordError.textContent = '两次输入的密码不一致';
        isValid = false;
    }

    // 验证电话号码
    const phoneRegex = /^1[3-9]\d{9}$/; // 简单的中国大陆手机号码正则表达式
    if (phone.trim() === '') {
        phoneError.textContent = '电话号码不能为空';
        isValid = false;
    } else if (!phoneRegex.test(phone)) {
        phoneError.textContent = '请输入有效的电话号码';
        isValid = false;
    }

    // 验证电子邮件
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 简单的电子邮件正则表达式
    if (email.trim() === '') {
        emailError.textContent = '电子邮件不能为空';
        isValid = false;
    } else if (!emailRegex.test(email)) {
        emailError.textContent = '请输入有效的电子邮件地址';
        isValid = false;
    }

    // 验证电话号码
    const phoneRegex = /^1[3-9]\d{9}$/; // 简单的中国大陆手机号码正则表达式
    if (phone.trim() === '') {
        error.textContent = '电话号码不能为空';
		error.style.display = 'block';
        isValid = false;
    } else if (!phoneRegex.test(phone)) {
        error.textContent = '请输入有效的电话号码';
		error.style.display = 'block';
        isValid = false;
    }

    // 验证电子邮件
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 简单的电子邮件正则表达式
    if (email.trim() === '') {
        error.textContent = '电子邮件不能为空';
		error.style.display = 'block';
        isValid = false;
    } else if (!emailRegex.test(email)) {
        error.textContent = '请输入有效的电子邮件地址';
		error.style.display = 'block';
        isValid = false;
    }

    // 验证电话号码
    const phoneRegex = /^1[3-9]\d{9}$/; // 简单的中国大陆手机号码正则表达式
    if (phone.trim() === '') {
        error.textContent = '电话号码不能为空';
		error.style.display = 'block';
        isValid = false;
    } else if (!phoneRegex.test(phone)) {
        error.textContent = '请输入有效的电话号码';
		error.style.display = 'block';
        isValid = false;
    }

    // 验证电子邮件
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 简单的电子邮件正则表达式
    if (email.trim() === '') {
        error.textContent = '电子邮件不能为空';
		error.style.display = 'block';
        isValid = false;
    } else if (!emailRegex.test(email)) {
        error.textContent = '请输入有效的电子邮件地址';
		error.style.display = 'block';
        isValid = false;
    }

    // 如果所有验证通过，则提交表单
    if (isValid) {
        document.getElementById('registrationForm').submit();
    }
});