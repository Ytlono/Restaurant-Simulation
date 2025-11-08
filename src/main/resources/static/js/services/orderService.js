// Сервис для работы с API заказов
class OrderService {
    constructor() {
        this.API_BASE_URL = 'http://localhost:8080/api/orders';
        this.currentPage = 0;
        this.pageSize = 20;
        this.totalPages = 0;
        this.currentFilters = {
            userIds: [],
            statuses: ['PENDING', 'IN_PROGRESS', 'COOKING', 'READY', 'SERVED'],
            minDate: null,
            maxDate: null
        };
        this.currentSorting = 'CREATED_AT_DESC';
    }

    async getOrders(page = this.currentPage, size = this.pageSize, sorting = this.currentSorting) {
        try {
            const requestBody = {
                statuses: this.currentFilters.statuses,
                minDate: this.currentFilters.minDate,
                maxDate: this.currentFilters.maxDate
            };

            Object.keys(requestBody).forEach(key => {
                if (requestBody[key] === null || (Array.isArray(requestBody[key]) && requestBody[key].length === 0)) {
                    delete requestBody[key];
                }
            });

            const url = `${this.API_BASE_URL}?page=${page}&size=${size}&orderSorting=${sorting}`;

            console.log('Order request:', {
                url: url,
                body: requestBody,
                page: page,
                size: size,
                sorting: sorting
            });

            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(requestBody)
            });

            if (!response.ok) {
                const errorText = await response.text();
                console.error('Error response:', errorText);
                throw new Error(`Ошибка загрузки заказов: ${response.status} - ${errorText}`);
            }

            const data = await response.json();

            console.log('Order response:', data);

            this.currentPage = data.number;
            this.totalPages = data.totalPages;
            this.pageSize = data.size;

            return {
                content: data.content,
                pageInfo: {
                    currentPage: data.number,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    size: data.size
                }
            };

        } catch (error) {
            console.error('Ошибка в getOrders:', error);
            throw error;
        }
    }

    updateFilters(newFilters) {
        this.currentFilters = { ...this.currentFilters, ...newFilters };
        this.currentPage = 0;
    }

    updateSorting(newSorting) {
        this.currentSorting = newSorting;
        this.currentPage = 0;
    }

    async goToPage(page) {
        if (page >= 0 && page < this.totalPages) {
            this.currentPage = page;
            return await this.getOrders();
        }
        throw new Error('Некорректный номер страницы');
    }

    async nextPage() {
        if (this.currentPage < this.totalPages - 1) {
            this.currentPage++;
            return await this.getOrders();
        }
        throw new Error('Это последняя страница');
    }

    async prevPage() {
        if (this.currentPage > 0) {
            this.currentPage--;
            return await this.getOrders();
        }
        throw new Error('Это первая страница');
    }

    async firstPage() {
        this.currentPage = 0;
        return await this.getOrders();
    }

    async lastPage() {
        this.currentPage = this.totalPages - 1;
        return await this.getOrders();
    }

    resetFilters() {
        this.currentFilters = {
            userIds: [],
            statuses: ['PENDING', 'IN_PROGRESS', 'COOKING', 'READY', 'SERVED'],
            minDate: null,
            maxDate: null
        };
        this.currentSorting = 'CREATED_AT_DESC';
        this.currentPage = 0;
    }
}