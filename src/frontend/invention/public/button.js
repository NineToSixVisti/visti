document.addEventListener('DOMContentLoaded', () => {
    const confirmButton = document.querySelector('.confirm-button');
    const apiEndpoint = confirmButton.getAttribute('data-api-endpoint');
    console.log(confirmButton);
    confirmButton.addEventListener('click', () => {
        console.log("안녕하세요");
        window.location.href = apiEndpoint; // 다른 페이지로 리디렉션
    });
});
