import axios from "axios";

const API_URL = 'http://localhost:8080/api';

const api = axios.create({
    baseURL:API_URL
});

api.interceptors.request.use((config) => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }

    if (userId) {
        config.headers['X-User-ID'] = userId;
    }
    return config;
}
);


export const getActivities = () => api.get('/activities');
export const addActivity = (activity) => {
  return api.post('/activities', {
    userId: localStorage.getItem('userId') || "demo-user",  // ensure userId
    type: activity.type.toUpperCase(),                      // enum uppercase
    duration: activity.duration,
    caloriesBurned: activity.caloriesBurned,
    startTime: new Date().toISOString(),                    // valid ISO string
    additionalMetrics: {}                                   // optional placeholder
  });
};

export const getActivityDetail = (id) => api.get(`/recommendations/activity/${id}`);