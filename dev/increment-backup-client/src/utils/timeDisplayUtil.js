export default {
    formatTime(time) {
        if (time < 1000) {
            return `${time}ms`;
        } else if (time < 60 * 1000) {
            return `${Math.floor(time / 1000)}s ${time % 1000}ms`;
        } else if (time < 60 * 60 * 1000) {
            const minutes = Math.floor(time / (60 * 1000));
            const seconds = Math.floor((time - minutes * 60 * 1000) / 1000);
            return `${minutes}m ${seconds}s`;
        } else if (time < 24 * 60 * 60 * 1000) {
            const hours = Math.floor(time / (60 * 60 * 1000));
            const minutes = Math.floor((time - hours * 60 * 60 * 1000) / (60 * 1000));
            const seconds = Math.floor((time - hours * 60 * 60 * 1000 - minutes * 60 * 1000) / 1000);
            return `${hours}h ${minutes}m ${seconds}s`;
        } else {
            const days = Math.floor(time / (24 * 60 * 60 * 1000));
            const hours = Math.floor((time - days * 24 * 60 * 60 * 1000) / (60 * 60 * 1000));
            const minutes = Math.floor((time - days * 24 * 60 * 60 * 1000 - hours * 60 * 60 * 1000) / (60 * 1000));
            const seconds = Math.floor((time - days * 24 * 60 * 60 * 1000 - hours * 60 * 60 * 1000 - minutes * 60 * 1000) / 1000);
            return `${days}d ${hours}h ${minutes}m ${seconds}s`;
        }
    }
}
