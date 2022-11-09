import axios from "axios";
import {tokenReIssueRequest} from "../api/LoginApi";

const serverIp = 'http://localhost:8090';

const httpRequest = axios.create({
    baseURL: serverIp,
    withCredentials: true,
    responseType: 'json'
})

httpRequest.interceptors.request.use(
    function(config) {

        const token = localStorage.getItem("Authorization");
        if(token !== null && token !== "")
            config.headers.Authorization = "Bearer " + token;

        return config;
    },
    function(error){
        return Promise.reject(error);
    }
);


httpRequest.interceptors.response.use(
    function(response) {
        return response;
    },
    async function(error) {

        const originalRequest = error.config;
        const accessToken = localStorage.getItem("Authorization");
        // Access Token 만료 시, 토큰 재발급
        if(accessToken && error.response.status === 401 && !originalRequest._retry) {
            const response = await tokenReIssueRequest();

            if(response.code === 200) {
                const token = response.data.accessToken;
                localStorage.setItem("Authorization", token);
                originalRequest._retry = true;

                originalRequest.headers.Authorization = "Bearer " + accessToken;
                return await axios(originalRequest);
            } else {
                localStorage.removeItem("Authorization");
            }
        }
        return Promise.reject(error);
    }
)

export default httpRequest;