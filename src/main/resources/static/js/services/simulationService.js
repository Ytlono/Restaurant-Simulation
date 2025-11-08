class SimulationService {
    constructor() {
        this.API_BASE_URL = 'http://localhost:8080/api/simulation';
    }

    async pause() {
        try {
            const response = await fetch(`${this.API_BASE_URL}/pause`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error(`Ошибка паузы: ${response.status}`);
            }

            return await response.text();
        } catch (error) {
            console.error('Ошибка при паузе симуляции:', error);
            throw error;
        }
    }

    async resume() {
        try {
            const response = await fetch(`${this.API_BASE_URL}/resume`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error(`Ошибка возобновления: ${response.status}`);
            }

            return await response.text();
        } catch (error) {
            console.error('Ошибка при возобновлении симуляции:', error);
            throw error;
        }
    }

    async getStatus() {
        try {
            const response = await fetch(`${this.API_BASE_URL}/status`);

            if (!response.ok) {
                throw new Error(`Ошибка получения статуса: ${response.status}`);
            }

            return await response.text();
        } catch (error) {
            console.error('Ошибка при получении статуса симуляции:', error);
            throw error;
        }
    }

      async updateCookThreshold(seconds) {
            const response = await fetch(`${this.API_BASE_URL}/threshold/cook?seconds=${seconds}`, {
                method: 'POST'
            });

            if (!response.ok) {
                throw new Error('Ошибка обновления порога повара');
            }

            return await response.text();
        }

        async updateOrderTakerThreshold(seconds) {
            const response = await fetch(`${this.API_BASE_URL}/threshold/order-taker?seconds=${seconds}`, {
                method: 'POST'
            });

            if (!response.ok) {
                throw new Error('Ошибка обновления порога принимающего');
            }

            return await response.text();
        }

        async updateCustomerThreshold(seconds) {
            const response = await fetch(`${this.API_BASE_URL}/threshold/customer?seconds=${seconds}`, {
                method: 'POST'
            });

            if (!response.ok) {
                throw new Error('Ошибка обновления порога клиента');
            }

            return await response.text();
        }
}