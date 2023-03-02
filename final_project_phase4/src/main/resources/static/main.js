const numbers = document.querySelectorAll('.num-active')

numbers[0].focus()

numbers.forEach((num,index) => {
    num.addEventListener("keydown", () => {
        if(num.value.length === (num.maxLength - 1)) {
            setTimeout(() => {
                numbers[index + 1].focus()
            },60)
        }
    })
})

let remainingTime = 600;
const remainingTimeElement = document.getElementById('remaining-time');
const timer = setInterval(() => {
    const minutes = Math.floor(remainingTime / 60);
    const seconds = remainingTime % 60;
    remainingTimeElement.textContent = ` Remaining time: ${minutes}:${seconds.toString().padStart(2, '0')}`;
    remainingTime--;
    if (remainingTime <= 0) {
        clearInterval(timer);
        handleTimeout();
    }
}, 1000);

function handleTimeout() {
    window.location.href = 'failed.html';
}