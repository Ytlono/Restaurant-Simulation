// Сервис для работы с API клиентов
class CustomerService {
    constructor() {
        this.API_BASE_URL = 'http://localhost:8080/api/customers';
    }

    // Получить всех клиентов
    async getAllCustomers() {
        const response = await fetch(this.API_BASE_URL);
        if (!response.ok) {
            throw new Error(`Ошибка загрузки: ${response.status}`);
        }
        return await response.json();
    }

    // Добавить нового клиента
    async addCustomer(customerData) {
        const response = await fetch(this.API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(customerData)
        });

        if (!response.ok) {
            throw new Error(`Ошибка добавления: ${response.status}`);
        }

        return await response.json();
    }

    // Удалить клиента
    async deleteCustomer(id) {
        const response = await fetch(`${this.API_BASE_URL}/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error(`Ошибка удаления: ${response.status}`);
        }
    }
}