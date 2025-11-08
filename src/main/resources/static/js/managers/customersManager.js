class CustomersManager {
    constructor(app) {
        this.app = app;
    }

    async loadCustomers() {
        try {
            const customers = await this.app.customerService.getAllCustomers();
            this.app.uiManager.displayCustomers(customers, 'customersGrid');
            this.app.uiManager.updateLastUpdateTime('customersLastUpdate');
        } catch (error) {
            this.app.uiManager.showError('customersError', error.message);
        }
    }

    async addCustomer() {
        const name = this.app.uiManager.showAddDialog('Введите имя клиента:');
        if (!name) return;

        try {
            await this.app.customerService.addCustomer({ name, status: 'AVAILABLE' });
            if (this.app.uiManager.currentTab === 'customers') {
                await this.loadCustomers();
            } else if (this.app.uiManager.currentTab === 'overview') {
                await this.app.overviewManager.loadOverview();
            }
        } catch (error) {
            alert(`Ошибка: ${error.message}`);
        }
    }
}