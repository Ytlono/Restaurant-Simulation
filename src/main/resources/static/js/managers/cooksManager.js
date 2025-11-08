class CooksManager {
    constructor(app) {
        this.app = app;
    }

    async loadCooks() {
        try {
            const cooks = await this.app.cookService.getAllCooks();
            this.app.uiManager.displayCooks(cooks, 'cooksGrid');
            this.app.uiManager.updateLastUpdateTime('cooksLastUpdate');
        } catch (error) {
            this.app.uiManager.showError('cooksError', error.message);
        }
    }

    async addCook() {
        const name = this.app.uiManager.showAddDialog('Введите имя повара:');
        if (!name) return;

        const speciality = this.app.uiManager.showAddDialog('Введите специальность повара:') || 'Универсал';

        try {
            await this.app.cookService.addCook({
                name,
                speciality,
                status: 'AVAILABLE'
            });
            if (this.app.uiManager.currentTab === 'cooks') {
                await this.loadCooks();
            } else if (this.app.uiManager.currentTab === 'overview') {
                await this.app.overviewManager.loadOverview();
            }
        } catch (error) {
            alert(`Ошибка: ${error.message}`);
        }
    }

    async deleteCook(cookId) {
        if (confirm('Вы уверены, что хотите удалить этого повара?')) {
            try {
                await this.app.cookService.deleteCook(cookId);
                if (this.app.uiManager.currentTab === 'cooks') {
                    await this.loadCooks();
                } else if (this.app.uiManager.currentTab === 'overview') {
                    await this.app.overviewManager.loadOverview();
                }
            } catch (error) {
                alert(`Ошибка при удалении: ${error.message}`);
            }
        }
    }

    async deleteCustomer(customerId) {
            if (confirm('Вы уверены, что хотите удалить этого клиента?')) {
                try {
                    await this.app.customerService.deleteCustomer(customerId);
                    if (this.app.uiManager.currentTab === 'customers') {
                        await this.loadCustomers();
                    } else if (this.app.uiManager.currentTab === 'overview') {
                        await this.app.overviewManager.loadOverview();
                    }
                } catch (error) {
                    alert(`Ошибка при удалении: ${error.message}`);
                }
            }
        }
}