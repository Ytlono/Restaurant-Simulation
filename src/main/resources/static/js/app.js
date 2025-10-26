class RestaurantApp {
    constructor() {
        this.customerService = new CustomerService();
        this.orderTakerService = new OrderTakerService();
        this.cookService = new CookService();
        this.uiManager = new UIManager();
        this.autoRefreshEnabled = true;
        this.refreshInterval = null;

        this.initializeEventListeners();
    }

    initializeEventListeners() {
        // Общая вкладка
        document.getElementById('refreshOverviewBtn').addEventListener('click', () => this.loadOverview());

        // Вкладка клиентов
        document.getElementById('refreshCustomersBtn').addEventListener('click', () => this.loadCustomers());
        document.getElementById('addCustomerBtn').addEventListener('click', () => this.addCustomer());

        // Вкладка принимающих
        document.getElementById('refreshOrderTakersBtn').addEventListener('click', () => this.loadOrderTakers());
        document.getElementById('addOrderTakerBtn').addEventListener('click', () => this.addOrderTaker());

        // Вкладка поваров
        document.getElementById('refreshCooksBtn').addEventListener('click', () => this.loadCooks());
        document.getElementById('addCookBtn').addEventListener('click', () => this.addCook());

        // Автообновление
        document.getElementById('autoRefreshToggle').addEventListener('click', () => this.toggleAutoRefresh());

        window.addEventListener('beforeunload', () => this.stopAutoRefresh());
    }

    // === ОБЩАЯ ВКЛАДКА ===
   // === ОБЩАЯ ВКЛАДКА ===
   async loadOverview() {
       try {
           this.uiManager.showLoading('overviewCustomersLoading');
           this.uiManager.showLoading('overviewOrderTakersLoading');
           this.uiManager.showLoading('overviewCooksLoading');

           // Загружаем все данные параллельно с индивидуальной обработкой ошибок
           const [customers, orderTakers, cooks] = await Promise.all([
               this.customerService.getAllCustomers().catch(error => {
                   console.error('Ошибка загрузки клиентов:', error);
                   return [];
               }),
               this.orderTakerService.getAllOrderTakers().catch(error => {
                   console.error('Ошибка загрузки принимающих:', error);
                   return [];
               }),
               this.cookService.getAllCooks().catch(error => {
                   console.error('Ошибка загрузки поваров:', error);
                   return [];
               })
           ]);

           this.uiManager.displayCustomers(customers, 'overviewCustomersGrid');
           this.uiManager.displayOrderTakers(orderTakers, 'overviewOrderTakersGrid');
           this.uiManager.displayCooks(cooks, 'overviewCooksGrid');
           this.uiManager.updateLastUpdateTime('overviewLastUpdate');

       } catch (error) {
           console.error('Неожиданная ошибка при загрузке общего обзора:', error);
       } finally {
           this.uiManager.hideLoading('overviewCustomersLoading');
           this.uiManager.hideLoading('overviewOrderTakersLoading');
           this.uiManager.hideLoading('overviewCooksLoading');
       }
   }

    // === КЛИЕНТЫ ===
    async loadCustomers() {
        try {
            this.uiManager.showLoading('customersLoading');
            const customers = await this.customerService.getAllCustomers();
            this.uiManager.displayCustomers(customers, 'customersGrid');
            this.uiManager.updateLastUpdateTime('customersLastUpdate');
        } catch (error) {
            this.uiManager.showError('customersError', error.message);
        } finally {
            this.uiManager.hideLoading('customersLoading');
        }
    }

    async addCustomer() {
        const name = this.uiManager.showAddDialog('Введите имя клиента:');
        if (!name) return;

        try {
            await this.customerService.addCustomer({ name, status: 'AVAILABLE' });
            if (this.uiManager.currentTab === 'customers') {
                await this.loadCustomers();
            } else if (this.uiManager.currentTab === 'overview') {
                await this.loadOverview();
            }
        } catch (error) {
            alert(`Ошибка: ${error.message}`);
        }
    }

    // === ПРИНИМАЮЩИЕ ЗАКАЗЫ ===
    async loadOrderTakers() {
        try {
            this.uiManager.showLoading('orderTakersLoading');
            const orderTakers = await this.orderTakerService.getAllOrderTakers();
            this.uiManager.displayOrderTakers(orderTakers, 'orderTakersGrid');
            this.uiManager.updateLastUpdateTime('orderTakersLastUpdate');
        } catch (error) {
            this.uiManager.showError('orderTakersError', error.message);
        } finally {
            this.uiManager.hideLoading('orderTakersLoading');
        }
    }

    async addOrderTaker() {
        const name = this.uiManager.showAddDialog('Введите имя принимающего:');
        if (!name) return;

        try {
            await this.orderTakerService.addOrderTaker({ name });
            if (this.uiManager.currentTab === 'orderTakers') {
                await this.loadOrderTakers();
            } else if (this.uiManager.currentTab === 'overview') {
                await this.loadOverview();
            }
        } catch (error) {
            alert(`Ошибка: ${error.message}`);
        }
    }

    // === ПОВАРА ===
    async loadCooks() {
        try {
            this.uiManager.showLoading('cooksLoading');
            const cooks = await this.cookService.getAllCooks();
            this.uiManager.displayCooks(cooks, 'cooksGrid');
            this.uiManager.updateLastUpdateTime('cooksLastUpdate');
        } catch (error) {
            this.uiManager.showError('cooksError', error.message);
        } finally {
            this.uiManager.hideLoading('cooksLoading');
        }
    }

    async addCook() {
        const name = this.uiManager.showAddDialog('Введите имя повара:');
        if (!name) return;

        const speciality = this.uiManager.showAddDialog('Введите специальность повара:') || 'Универсал';

        try {
            await this.cookService.addCook({
                name,
                speciality,
                status: 'AVAILABLE'
            });
            if (this.uiManager.currentTab === 'cooks') {
                await this.loadCooks();
            } else if (this.uiManager.currentTab === 'overview') {
                await this.loadOverview();
            }
        } catch (error) {
            alert(`Ошибка: ${error.message}`);
        }
    }

    // === АВТООБНОВЛЕНИЕ ===
    toggleAutoRefresh() {
        this.autoRefreshEnabled = !this.autoRefreshEnabled;
        const toggleButton = document.getElementById('autoRefreshToggle');

        if (this.autoRefreshEnabled) {
            toggleButton.textContent = 'Автообновление: ВКЛ';
            this.startAutoRefresh();
        } else {
            toggleButton.textContent = 'Автообновление: ВЫКЛ';
            this.stopAutoRefresh();
        }
    }

    startAutoRefresh() {
        this.refreshInterval = setInterval(() => {
            if (this.uiManager.currentTab === 'overview') {
                this.loadOverview();
            } else if (this.uiManager.currentTab === 'customers') {
                this.loadCustomers();
            } else if (this.uiManager.currentTab === 'orderTakers') {
                this.loadOrderTakers();
            } else if (this.uiManager.currentTab === 'cooks') {
                this.loadCooks();
            }
        }, 1000);
    }

    stopAutoRefresh() {
        if (this.refreshInterval) {
            clearInterval(this.refreshInterval);
        }
    }

    init() {
        this.loadOverview();
        this.startAutoRefresh();
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new RestaurantApp().init();
});