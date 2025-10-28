// Менеджер пользовательского интерфейса
class UIManager {
    constructor() {
        this.currentTab = 'overview';
        this.initializeTabSystem();
    }

    // === Инициализация вкладок ===
    initializeTabSystem() {
        const tabButtons = document.querySelectorAll('.tab-button');
        tabButtons.forEach(button => {
            button.addEventListener('click', (e) => {
                const tabName = e.target.getAttribute('data-tab');
                this.switchTab(tabName);
            });
        });
    }

    switchTab(tabName) {
        document.querySelectorAll('.tab-button').forEach(btn => btn.classList.remove('active'));
        document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));

        document.querySelector(`[data-tab="${tabName}"]`).classList.add('active');
        document.getElementById(`${tabName}-tab`).classList.add('active');
        this.currentTab = tabName;
    }

    // === Общие методы ===
    showLoading(elementId) { document.getElementById(elementId).style.display = 'block'; }
    hideLoading(elementId) { document.getElementById(elementId).style.display = 'none'; }

    showError(elementId, message) {
        const errorElement = document.getElementById(elementId);
        errorElement.textContent = `Ошибка: ${message}`;
        errorElement.style.display = 'block';
    }

    hideError(elementId) { document.getElementById(elementId).style.display = 'none'; }

    updateLastUpdateTime(elementId) {
        const now = new Date();
        document.getElementById(elementId).textContent = `Последнее обновление: ${now.toLocaleTimeString()}`;
    }

    // === Клиенты ===
    getCustomerStatusText(status) {
        const statusMap = {
            'AVAILABLE': 'Свободен',
            'WAITING_FOR_ORDER': 'Ждет заказ',
            'WAITING_FOR_FOOD': 'Ждет еду',
            'EATING': 'Ест'
        };
        return statusMap[status] || status;
    }

    getCustomerStatusClass(status) {
        const classMap = {
            'AVAILABLE': 'status-available',
            'WAITING_FOR_ORDER': 'status-waiting-for-order',
            'WAITING_FOR_FOOD': 'status-waiting-for-food',
            'EATING': 'status-eating'
        };
        return classMap[status] || 'status-available';
    }

    displayCustomers(customers, containerId) {
        const grid = document.getElementById(containerId);
        grid.innerHTML = '';

        if (customers.length === 0) {
            grid.innerHTML = '<p style="grid-column: 1/-1; text-align:center;color:#666;">Клиенты не найдены</p>';
            return;
        }

        customers.forEach(customer => this.createCustomerCard(customer, containerId));
    }

    createCustomerCard(customer, containerId) {
        const customerCard = document.createElement('div');
        customerCard.className = `customer-card ${this.getCustomerStatusClass(customer.status)}`;
        customerCard.innerHTML = `
            <div class="customer-id">#${customer.id}</div>
            <div class="customer-name">${customer.name || 'Клиент #' + customer.id}</div>
            <div class="customer-status">${this.getCustomerStatusText(customer.status)}</div>
        `;
        document.getElementById(containerId).appendChild(customerCard);
    }

    // === Принимающие заказы ===
    displayOrderTakers(orderTakers, containerId) {
        const grid = document.getElementById(containerId);
        grid.innerHTML = '';

        if (orderTakers.length === 0) {
            grid.innerHTML = '<p style="grid-column:1/-1;text-align:center;color:#666;">Принимающие заказы не найдены</p>';
            return;
        }

        orderTakers.forEach(orderTaker => this.createOrderTakerCard(orderTaker, containerId));
    }

    createOrderTakerCard(orderTaker, containerId) {
        const orderTakerCard = document.createElement('div');
        orderTakerCard.className = `order-taker-card ${this.getOrderTakerStatusClass(orderTaker.status)}`;
        orderTakerCard.innerHTML = `
            <div class="order-taker-id">#${orderTaker.id}</div>
            <div class="order-taker-name">${orderTaker.name || 'Принимающий #' + orderTaker.id}</div>
            <div class="order-taker-status">${this.getOrderTakerStatusText(orderTaker.status)}</div>
        `;
        document.getElementById(containerId).appendChild(orderTakerCard);
    }

    getOrderTakerStatusClass(status) {
        const statusClassMap = {
            'AVAILABLE': 'available',
            'PROCESSING': 'busy'
        };
        return statusClassMap[status] || 'available';
    }

    getOrderTakerStatusText(status) {
        const statusTextMap = {
            'AVAILABLE': 'Свободен',
            'PROCESSING': 'Обрабатывает заказ'
        };
        return statusTextMap[status] || status;
    }

    // === Повара ===
    getCookStatusText(status) {
        const statusMap = {
            'AVAILABLE': 'Свободен',
            'PROCESSING': 'Готовит',
            'BUSY': 'Занят'
        };
        return statusMap[status] || status;
    }

    getCookStatusClass(status) {
        const classMap = {
            'AVAILABLE': 'status-available',
            'PROCESSING': 'status-processing',
            'BUSY': 'status-busy'
        };
        return classMap[status] || 'status-available';
    }

    displayCooks(cooks, containerId) {
        const grid = document.getElementById(containerId);
        grid.innerHTML = '';

        if (cooks.length === 0) {
            grid.innerHTML = '<p style="grid-column:1/-1;text-align:center;color:#666;">Повара не найдены</p>';
            return;
        }

        cooks.forEach(cook => this.createCookCard(cook, containerId));
    }

    createCookCard(cook, containerId) {
        const cookCard = document.createElement('div');
        cookCard.className = `cook-card ${this.getCookStatusClass(cook.status)}`;

        const details = [];
        if (cook.orderId) details.push(`Заказ: #${cook.orderId}`);
        if (cook.kitchenTicketId) details.push(`Тикет: #${cook.kitchenTicketId}`);

        cookCard.innerHTML = `
            <div class="cook-id">#${cook.id}</div>
            <div class="cook-name">${cook.name || 'Повар #' + cook.id}</div>
            ${cook.speciality ? `<div class="cook-speciality">${cook.speciality}</div>` : ''}
            <div class="cook-status">${this.getCookStatusText(cook.status)}</div>
            ${details.length > 0 ? `<div class="cook-details">${details.join(' • ')}</div>` : ''}
        `;

        document.getElementById(containerId).appendChild(cookCard);
    }

    // === Диалоги ===
    showAddDialog(title) {
        return prompt(title);
    }

    getOrderStatusText(status) {
            const statusMap = {
                'PENDING': 'Ожидание',
                'IN_PROGRESS': 'В процессе',
                'COOKING': 'Готовится',
                'READY': 'Готов',
                'SERVED': 'Подано'
            };
            return statusMap[status] || status;
        }

        getOrderStatusClass(status) {
            const classMap = {
                'PENDING': 'status-pending',
                'IN_PROGRESS': 'status-in_progress',
                'COOKING': 'status-cooking',
                'READY': 'status-ready',
                'SERVED': 'status-served'
            };
            return classMap[status] || 'status-pending';
        }

        displayOrders(orders, pageInfo, containerId) {
            const grid = document.getElementById(containerId);
            if (!grid) return;

            grid.innerHTML = '';

            if (!orders || orders.length === 0) {
                grid.innerHTML = '<p style="grid-column: 1/-1; text-align: center; color: #666;">Заказы не найдены</p>';
                return;
            }

            orders.forEach(order => {
                this.createOrderCard(order, containerId);
            });

            // Обновляем информацию о пагинации
            this.updatePaginationInfo(pageInfo);
        }

        createOrderCard(order, containerId) {
            const grid = document.getElementById(containerId);
            if (!grid) return;

            const orderCard = document.createElement('div');
            orderCard.className = `order-card ${this.getOrderStatusClass(order.status)}`;

            const createdDate = order.createdAt ? new Date(order.createdAt).toLocaleString() : 'Неизвестно';
            const updatedDate = order.updatedAt ? new Date(order.updatedAt).toLocaleString() : 'Неизвестно';

            // ИСПРАВЛЕНИЕ: используем customerName вместо customer.name
            orderCard.innerHTML = `
                <div class="order-id">#${order.id}</div>
                <div class="order-customer">
                    Клиент: ${order.customerName || 'Неизвестен'}
                    ${order.customerId ? `(ID: ${order.customerId})` : ''}
                </div>
                <div class="order-status">${this.getOrderStatusText(order.status)}</div>
                <div class="order-timestamp">
                    Создан: ${createdDate}<br>
                    Обновлен: ${updatedDate}
                </div>
            `;

            grid.appendChild(orderCard);
        }

        // Обновление информации о пагинации
        updatePaginationInfo(pageInfo) {
            const pageInfoElement = document.getElementById('pageInfo');
            const firstPageBtn = document.getElementById('firstPageBtn');
            const prevPageBtn = document.getElementById('prevPageBtn');
            const nextPageBtn = document.getElementById('nextPageBtn');
            const lastPageBtn = document.getElementById('lastPageBtn');

            if (pageInfoElement && pageInfo) {
                pageInfoElement.textContent = `Страница ${pageInfo.currentPage + 1} из ${pageInfo.totalPages}`;
            }

            // Обновляем состояние кнопок
            if (firstPageBtn) firstPageBtn.disabled = !pageInfo || pageInfo.currentPage === 0;
            if (prevPageBtn) prevPageBtn.disabled = !pageInfo || pageInfo.currentPage === 0;
            if (nextPageBtn) nextPageBtn.disabled = !pageInfo || pageInfo.currentPage === pageInfo.totalPages - 1;
            if (lastPageBtn) lastPageBtn.disabled = !pageInfo || pageInfo.currentPage === pageInfo.totalPages - 1;
        }

        // Получение текущих фильтров из формы
        getCurrentFilters() {
            const filters = {
                statuses: [],
                minDate: null,
                maxDate: null
            };

            // Собираем выбранные статусы
            const statusCheckboxes = document.querySelectorAll('input[name="status"]:checked');
            filters.statuses = Array.from(statusCheckboxes).map(cb => cb.value);

            // Получаем даты
            const minDateInput = document.getElementById('minDate');
            const maxDateInput = document.getElementById('maxDate');

            if (minDateInput && minDateInput.value) {
                filters.minDate = new Date(minDateInput.value).toISOString();
            }

            if (maxDateInput && maxDateInput.value) {
                filters.maxDate = new Date(maxDateInput.value).toISOString();
            }

            // Получаем сортировку
            const sortingSelect = document.getElementById('orderSorting');
            if (sortingSelect) {
                this.currentOrderSorting = sortingSelect.value;
            }

            return filters;
        }

        // Сброс фильтров в форме
        resetFiltersForm() {
            // Сбрасываем все чекбоксы статусов
            const statusCheckboxes = document.querySelectorAll('input[name="status"]');
            statusCheckboxes.forEach(cb => cb.checked = true);

            // Сбрасываем даты
            const minDateInput = document.getElementById('minDate');
            const maxDateInput = document.getElementById('maxDate');
            if (minDateInput) minDateInput.value = '';
            if (maxDateInput) maxDateInput.value = '';

            // Сбрасываем сортировку
            const sortingSelect = document.getElementById('orderSorting');
            if (sortingSelect) sortingSelect.value = 'CREATED_AT_DESC';
        }
}
