class SimulationManager {
    constructor(app) {
        this.app = app;
        this.isPaused = false;
        this.statusCheckInterval = null;
    }

    initializeEventListeners() {
        document.getElementById('pauseSimulationBtn').addEventListener('click', () => this.pauseSimulation());

        document.getElementById('resumeSimulationBtn').addEventListener('click', () => this.resumeSimulation());

        this.startStatusChecking();
    }

    async pauseSimulation() {
        try {
            await this.app.simulationService.pause();
            this.setPausedState(true);
            this.showNotification('Симуляция поставлена на паузу', 'success');
        } catch (error) {
            console.error('Ошибка при паузе симуляции:', error);
            this.showNotification('Ошибка при паузе симуляции', 'error');
        }
    }

    async resumeSimulation() {
        try {
            await this.app.simulationService.resume();
            this.setPausedState(false);
            this.showNotification('Симуляция возобновлена', 'success');
        } catch (error) {
            console.error('Ошибка при возобновлении симуляции:', error);
            this.showNotification('Ошибка при возобновлении симуляции', 'error');
        }
    }

    async checkStatus() {
        try {
            const status = await this.app.simulationService.getStatus();
            this.updateStatusDisplay(status);
        } catch (error) {
            console.error('Ошибка при проверке статуса:', error);
        }
    }

    setPausedState(paused) {
        this.isPaused = paused;

        const pauseBtn = document.getElementById('pauseSimulationBtn');
        const resumeBtn = document.getElementById('resumeSimulationBtn');
        const statusElement = document.getElementById('simulationStatus');

        if (paused) {
            pauseBtn.style.display = 'none';
            resumeBtn.style.display = 'inline-block';
            statusElement.textContent = 'Симуляция на паузе';
            statusElement.className = 'simulation-status paused';

            this.app.stopAutoRefresh();
        } else {
            pauseBtn.style.display = 'inline-block';
            resumeBtn.style.display = 'none';
            statusElement.textContent = 'Симуляция активна';
            statusElement.className = 'simulation-status active';

            this.app.startAutoRefresh();
        }
    }

    updateStatusDisplay(statusText) {
        const statusElement = document.getElementById('simulationStatus');
        statusElement.textContent = statusText;
    }

    startStatusChecking() {
        this.statusCheckInterval = setInterval(() => {
            this.checkStatus();
        }, 10000);

        this.checkStatus();
    }

    showNotification(message, type) {
        alert(message);
    }

    stopStatusChecking() {
        if (this.statusCheckInterval) {
            clearInterval(this.statusCheckInterval);
        }
    }
}