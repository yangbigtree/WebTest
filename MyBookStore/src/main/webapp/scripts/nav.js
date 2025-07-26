// 显示消息列表
document.querySelector('.message-notifications').addEventListener('click', function() {
    var messageList = document.querySelector('.message-list');
    messageList.classList.toggle('active');
});
// 关闭消息列表
window.addEventListener('click', function(event) {
    var messageList = document.querySelector('.message-list');
    if (!messageList.contains(event.target)) {
        messageList.classList.remove('active');
    }
});
// 显示书籍菜单
var bookItems = document.querySelectorAll('.book-item');
bookItems.forEach(function(bookItem) {
    bookItem.addEventListener('mouseenter', function() {
        this.querySelector('.dropdown').style.display = 'block';
    });
    bookItem.addEventListener('mouseleave', function() {
        this.querySelector('.dropdown').style.display = 'none';
    });
});