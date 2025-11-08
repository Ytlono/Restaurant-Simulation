console.log('=== cookService.js загружен ===');

class CookService {
    constructor() {
        this.API_BASE_URL = 'http://localhost:8080/api/cooks';
    }

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

    async deleteCook(cookId) {
            try {
                const response = await fetch(`/api/cooks/${cookId}`, {
                    method: 'DELETE'
                });

                if (!response.ok) {
                    throw new Error('Ошибка при удалении повара');
                }

                return await response.json();
            } catch (error) {
                console.error('Ошибка при удалении повара:', error);
                throw error;
            }
        }
}