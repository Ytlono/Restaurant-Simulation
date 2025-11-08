class OrdersManager {
    constructor(app) {
        this.app = app;
    }

    async loadOrders() {
        try {
            const result = await this.app.orderService.getOrders();
            console.log('=== ORDERS RESPONSE ===');
            console.log('Full response:', result);

            if (result.content && result.content.length > 0) {
                console.log('Number of orders:', result.content.length);
                result.content.forEach((order, index) => {
                    console.log(`Order ${index + 1}:`, {
                        id: order.id,
                        customerId: order.customerId,
                        customerName: order.customerName,
                        status: order.status,
                        createdAt: order.createdAt,
                        updatedAt: order.updatedAt
                    });
                });
            } else {
                console.log('No orders found');
            }

            this.app.uiManager.displayOrders(result.content, result.pageInfo, 'ordersGrid');
            this.app.uiManager.updateLastUpdateTime('ordersLastUpdate');

        } catch (error) {
            console.error('Ошибка при загрузке заказов:', error);
            this.app.uiManager.showError('ordersError', error.message);
        }
    }

    async applyFilters() {
        try {
            const filters = this.app.uiManager.getCurrentFilters();
            this.app.orderService.updateFilters(filters);

            const sorting = document.getElementById('orderSorting').value;
            this.app.orderService.updateSorting(sorting);

            await this.loadOrders();

        } catch (error) {
            console.error('Ошибка при применении фильтров:', error);
            this.app.uiManager.showError('ordersError', error.message);
        }
    }

    async resetFilters() {
        try {
            this.app.orderService.resetFilters();
            this.app.uiManager.resetFiltersForm();
            await this.loadOrders();

        } catch (error) {
            console.error('Ошибка при сбросе фильтров:', error);
            this.app.uiManager.showError('ordersError', error.message);
        }
    }

    async goToFirstPage() {
        try {
            const result = await this.app.orderService.firstPage();
            this.app.uiManager.displayOrders(result.content, result.pageInfo, 'ordersGrid');
        } catch (error) {
            console.error('Ошибка перехода на первую страницу:', error);
        }
    }

    async goToPrevPage() {
        try {
            const result = await this.app.orderService.prevPage();
            this.app.uiManager.displayOrders(result.content, result.pageInfo, 'ordersGrid');
        } catch (error) {
            console.error('Ошибка перехода на предыдущую страницу:', error);
        }
    }

    async goToNextPage() {
        try {
            const result = await this.app.orderService.nextPage();
            this.app.uiManager.displayOrders(result.content, result.pageInfo, 'ordersGrid');
        } catch (error) {
            console.error('Ошибка перехода на следующую страницу:', error);
        }
    }

    async goToLastPage() {
        try {
            const result = await this.app.orderService.lastPage();
            this.app.uiManager.displayOrders(result.content, result.pageInfo, 'ordersGrid');
        } catch (error) {
            console.error('Ошибка перехода на последнюю страницу:', error);
        }
    }

    toggleOrdersAutoRefresh() {
        this.app.ordersAutoRefreshEnabled = !this.app.ordersAutoRefreshEnabled;
        const toggleButton = document.getElementById('autoRefreshToggleOrders');

        if (this.app.ordersAutoRefreshEnabled) {
            toggleButton.textContent = 'Автообновление: ВКЛ';
            this.startOrdersAutoRefresh();
        } else {
            toggleButton.textContent = 'Автообновление: ВЫКЛ';
            this.stopOrdersAutoRefresh();
        }
    }

    startOrdersAutoRefresh() {
        this.app.ordersRefreshInterval = setInterval(() => {
            if (this.app.uiManager.currentTab === 'orders') {
                this.loadOrders();
            }
        }, 5000);
    }

    stopOrdersAutoRefresh() {
        if (this.app.ordersRefreshInterval) {
            clearInterval(this.app.ordersRefreshInterval);
        }
    }
}