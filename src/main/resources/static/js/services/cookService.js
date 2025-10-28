// Сервис для работы с API поваров
console.log('=== cookService.js загружен ===');

class CookService {
    constructor() {
        this.API_BASE_URL = 'http://localhost:8080/api/cooks';
    }

    // Получить всех поваров
    async getAllCooks() {
        try {
            const response = await fetch(this.API_BASE_URL);
            if (!response.ok) {
                throw new Error(`Ошибка загрузки поваров: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('Ошибка в getAllCooks:', error);
            throw error;
        }
    }

    // Добавить нового повара
    async addCook(cookData) {
        try {
            const response = await fetch(this.API_BASE_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(cookData)
            });

            if (!response.ok) {
                throw new Error(`Ошибка добавления повара: ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Ошибка в addCook:', error);
            throw error;
        }
    }

    // Удалить повара
    async deleteCook(id) {
        try {
            const response = await fetch(`${this.API_BASE_URL}/${id}`, {
                method: 'DELETE'
            });

            if (!response.ok) {
                throw new Error(`Ошибка удаления повара: ${response.status}`);
            }
        } catch (error) {
            console.error('Ошибка в deleteCook:', error);
            throw error;
        }
    }
}