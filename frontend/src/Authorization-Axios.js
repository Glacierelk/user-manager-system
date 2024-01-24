import axios from 'axios';

const instance = axios.create({
    timeout: 5000
});

instance.interceptors.request.use(config => {
    try {
        const userId = localStorage.getItem("User-Id");
        if (userId) {
            config.headers['User-Id'] = userId;
        }
    } catch (e) { }

    return config;
}, error => {
    return Promise.reject(error);
});

export default instance;
