class RestaurantApp {
    constructor() {
        this.services = this.initializeServices();
        this.managers = this.initializeManagers();
        this.state = this.initializeState();

        this.initializeEventListeners();
        window.app = this;
    }

    initializeServices() {
        return {
            customerService: new CustomerService(),
            orderTakerService: new OrderTakerService(),
            cookService: new CookService(),
            orderService: new OrderService(),
            simulationService: new SimulationService()
        };
    }

    initializeManagers() {
        return {
            uiManager: new UIManager(),
            overviewManager: new OverviewManager(this),
            customersManager: new CustomersManager(this),
            orderTakersManager: new OrderTakersManager(this),
            cooksManager: new CooksManager(this),
            ordersManager: new OrdersManager(this),
            simulationManager: new SimulationManager(this)
        };
    }

    initializeState() {
        return {
            autoRefreshEnabled: true,
            ordersAutoRefreshEnabled: false,
            refreshInterval: null,
            ordersRefreshInterval: null
        };
    }

    get customerService() { return this.services.customerService; }
    get orderTakerService() { return this.services.orderTakerService; }
    get cookService() { return this.services.cookService; }
    get orderService() { return this.services.orderService; }
    get simulationService() { return this.services.simulationService; }

    get uiManager() { return this.managers.uiManager; }
    get overviewManager() { return this.managers.overviewManager; }
    get customersManager() { return this.managers.customersManager; }
    get orderTakersManager() { return this.managers.orderTakersManager; }
    get cooksManager() { return this.managers.cooksManager; }
    get ordersManager() { return this.managers.ordersManager; }
    get simulationManager() { return this.managers.simulationManager; }

    get autoRefreshEnabled() { return this.state.autoRefreshEnabled; }
    set autoRefreshEnabled(value) { this.state.autoRefreshEnabled = value; }

    get ordersAutoRefreshEnabled() { return this.state.ordersAutoRefreshEnabled; }
    set ordersAutoRefreshEnabled(value) { this.state.ordersAutoRefreshEnabled = value; }

    get refreshInterval() { return this.state.refreshInterval; }
    set refreshInterval(value) { this.state.refreshInterval = value; }

    get ordersRefreshInterval() { return this.state.ordersRefreshInterval; }
    set ordersRefreshInterval(value) { this.state.ordersRefreshInterval = value; }

    initializeEventListeners() {
        document.getElementById('refreshOverviewBtn').addEventListener('click', () => this.overviewManager.loadOverview());

        document.getElementById('refreshCustomersBtn').addEventListener('click', () => this.customersManager.loadCustomers());
        document.getElementById('addCustomerBtn').addEventListener('click', () => this.customersManager.addCustomer());

        document.getElementById('refreshOrderTakersBtn').addEventListener('click', () => this.orderTakersManager.loadOrderTakers());
        document.getElementById('addOrderTakerBtn').addEventListener('click', () => this.orderTakersManager.addOrderTaker());

        document.getElementById('refreshCooksBtn').addEventListener('click', () => this.cooksManager.loadCooks());
        document.getElementById('addCookBtn').addEventListener('click', () => this.cooksManager.addCook());

        document.getElementById('refreshOrdersBtn').addEventListener('click', () => this.ordersManager.loadOrders());
        document.getElementById('applyFiltersBtn').addEventListener('click', () => this.ordersManager.applyFilters());
        document.getElementById('resetFiltersBtn').addEventListener('click', () => this.ordersManager.resetFilters());
        document.getElementById('autoRefreshToggleOrders').addEventListener('click', () => this.ordersManager.toggleOrdersAutoRefresh());

        document.getElementById('firstPageBtn').addEventListener('click', () => this.ordersManager.goToFirstPage());
        document.getElementById('prevPageBtn').addEventListener('click', () => this.ordersManager.goToPrevPage());
        document.getElementById('nextPageBtn').addEventListener('click', () => this.ordersManager.goToNextPage());
        document.getElementById('lastPageBtn').addEventListener('click', () => this.ordersManager.goToLastPage());


        document.getElementById('updateCookThresholdBtn').addEventListener('click', () => this.updateCookThreshold());
        document.getElementById('updateOrderTakerThresholdBtn').addEventListener('click', () => this.updateOrderTakerThreshold());
        document.getElementById('updateCustomerThresholdBtn').addEventListener('click', () => this.updateCustomerThreshold());
        this.simulationManager.initializeEventListeners();

        document.getElementById('autoRefreshToggle').addEventListener('click', () => this.toggleAutoRefresh());

        window.addEventListener('beforeunload', () => this.stopAutoRefresh());
    }

    async updateCookThreshold() {
        const seconds = document.getElementById('cookThresholdInput').value;
        try {
            await this.simulationService.updateCookThreshold(seconds);
            alert(`Порог повара изменён на ${seconds} сек.`);
        } catch (error) {
            alert(`Ошибка: ${error.message}`);
        }
    }

    async updateOrderTakerThreshold() {
        const seconds = document.getElementById('orderTakerThresholdInput').value;
        try {
            await this.simulationService.updateOrderTakerThreshold(seconds);
            alert(`Порог принимающего изменён на ${seconds} сек.`);
        } catch (error) {
            alert(`Ошибка: ${error.message}`);
        }
    }

    async updateCustomerThreshold() {
        const seconds = document.getElementById('customerThresholdInput').value;
        try {
            await this.simulationService.updateCustomerThreshold(seconds);
            alert(`Порог клиента изменён на ${seconds} сек.`);
        } catch (error) {
            alert(`Ошибка: ${error.message}`);
        }
    }
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
                this.overviewManager.loadOverview();
            } else if (this.uiManager.currentTab === 'customers') {
                this.customersManager.loadCustomers();
            } else if (this.uiManager.currentTab === 'orderTakers') {
                this.orderTakersManager.loadOrderTakers();
            } else if (this.uiManager.currentTab === 'cooks') {
                this.cooksManager.loadCooks();
            }
        }, 1000);
    }

    stopAutoRefresh() {
        if (this.refreshInterval) {
            clearInterval(this.refreshInterval);
        }
        this.ordersManager.stopOrdersAutoRefresh();
        this.simulationManager.stopStatusChecking();
    }

    init() {
        this.overviewManager.loadOverview();
        this.startAutoRefresh();
    }

}

document.addEventListener('DOMContentLoaded', () => {
    new RestaurantApp().init();
});