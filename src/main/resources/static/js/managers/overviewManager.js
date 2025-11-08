class OverviewManager {
    constructor(app) {
        this.app = app;
    }

    async loadOverview() {
        try {
            const [customers, orderTakers, cooks] = await Promise.all([
                this.app.customerService.getAllCustomers().catch(error => {
                    console.error('Ошибка загрузки клиентов:', error);
                    return [];
                }),
                this.app.orderTakerService.getAllOrderTakers().catch(error => {
                    console.error('Ошибка загрузки принимающих:', error);
                    return [];
                }),
                this.app.cookService.getAllCooks().catch(error => {
                    console.error('Ошибка загрузки поваров:', error);
                    return [];
                })
            ]);

            this.app.uiManager.displayCustomers(customers, 'overviewCustomersGrid');
            this.app.uiManager.displayOrderTakers(orderTakers, 'overviewOrderTakersGrid');
            this.app.uiManager.displayCooks(cooks, 'overviewCooksGrid');
            this.app.uiManager.updateLastUpdateTime('overviewLastUpdate');

        } catch (error) {
            console.error('Неожиданная ошибка при загрузке общего обзора:', error);
        }
    }
}