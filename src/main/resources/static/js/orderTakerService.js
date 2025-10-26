// Сервис для работы с API принимающих заказы
class OrderTakerService {
    constructor() {
        this.API_BASE_URL = 'http://localhost:8080/api/order-tackers';
    }

    // Получить всех принимающих заказы
    async getAllOrderTakers() {
        const response = await fetch(this.API_BASE_URL);
        if (!response.ok) {
            throw new Error(`Ошибка загрузки принимающих заказы: ${response.status}`);
        }
        return await response.json();
    }

    // Добавить нового принимающего заказы
    async addOrderTaker(orderTakerData) {
        const response = await fetch(this.API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderTakerData)
        });

        if (!response.ok) {
            throw new Error(`Ошибка добавления принимающего: ${response.status}`);
        }

        return await response.json();
    }

    // Удалить принимающего заказы
    async deleteOrderTaker(id) {
        const response = await fetch(`${this.API_BASE_URL}/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error(`Ошибка удаления принимающего: ${response.status}`);
        }
    }
}