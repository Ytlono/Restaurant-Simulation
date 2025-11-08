class OrderTakersManager {
    constructor(app) {
        this.app = app;
    }

    async loadOrderTakers() {
        try {
            this.app.uiManager.showLoading('orderTakersLoading');
            const orderTakers = await this.app.orderTakerService.getAllOrderTakers();
            this.app.uiManager.displayOrderTakers(orderTakers, 'orderTakersGrid');
            this.app.uiManager.updateLastUpdateTime('orderTakersLastUpdate');
        } catch (error) {
            this.app.uiManager.showError('orderTakersError', error.message);
        } finally {
            this.app.uiManager.hideLoading('orderTakersLoading');
        }
    }

    async addOrderTaker() {
        const name = this.app.uiManager.showAddDialog('Введите имя принимающего:');
        if (!name) return;

        try {
            await this.app.orderTakerService.addOrderTaker({ name });
            if (this.app.uiManager.currentTab === 'orderTakers') {
                await this.loadOrderTakers();
            } else if (this.app.uiManager.currentTab === 'overview') {
                await this.app.overviewManager.loadOverview();
            }
        } catch (error) {
            alert(`Ошибка: ${error.message}`);
        }
    }

    async deleteOrderTaker(orderTakerId) {
            if (confirm('Вы уверены, что хотите удалить этого принимающего заказы?')) {
                try {
                    await this.app.orderTakerService.deleteOrderTaker(orderTakerId);
                    if (this.app.uiManager.currentTab === 'orderTakers') {
                        await this.loadOrderTakers();
                    } else if (this.app.uiManager.currentTab === 'overview') {
                        await this.app.overviewManager.loadOverview();
                    }
                } catch (error) {
                    alert(`Ошибка при удалении: ${error.message}`);
                }
            }
        }
}