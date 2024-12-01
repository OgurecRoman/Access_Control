console.log("app.js успешно загружен");
document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM полностью загружен и готов.");
        const alertButton = document.getElementById("alertButton");
        function checkFaceStatus() {
            console.log("Запрос на получение статуса лица...");
            fetch('/poll', {
                method: 'GET'
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP ошибка! статус: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log("Статус лица от сервера:", data);
                // Убедимся, что data строго boolean
                if (typeof data !== "boolean") {
                    console.error("Ошибка: некорректный тип данных от сервера. Ожидался boolean.");
                    return;
                }
                // Обновляем кнопку на основе статуса
                if (!data) {
                    alertButton.classList.add('visible');
                    console.log("Кнопка вызова охраны отображена.");
                } else {
                    alertButton.classList.remove('visible');
                    console.log("Кнопка вызова охраны скрыта.");
                }
            })
            .catch(error => {
                console.error("Ошибка при получении статуса:", error);
            });
        }
        // Проверяем статус каждые 10 секунд
        setInterval(checkFaceStatus, 10000);
        // Запускаем проверку сразу после загрузки страницы
        checkFaceStatus();
 });
