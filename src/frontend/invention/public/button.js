document.addEventListener('DOMContentLoaded', () => {
    const confirmButton = document.querySelector('.confirm-button');
    const apiEndpoint = confirmButton.getAttribute('data-api-endpoint');
    console.log(confirmButton);
    confirmButton.addEventListener('click', () => {
        console.log("안녕하세요");
        window.location.href = "https://www.visti.com/Gbq4M91_5umz3hAs9Ios_g"; // 다른 페이지로 리디렉션
    });
});
