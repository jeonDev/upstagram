import axios from "axios";
import {tokenReIssueRequest} from "../api/LoginApi";

const serverIp = process.env.REACT_APP_SERVER;

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

        // Access Token 만료 시, 토큰 재발급
        if(error.response.status === 401 && !originalRequest._retry) {

            const accessToken = localStorage.getItem("Authorization");
            if(!accessToken) {
                alert("로그인 후 이용해주세요.");
                window.location.href = '/login';
                return false;
            }

            const response = await tokenReIssueRequest();

            if(response.code === 200) {
                const token = response.data.accessToken;
                localStorage.setItem("Authorization", token);
                originalRequest._retry = true;

                originalRequest.headers = {
                    ...originalRequest.headers
                }
                
                return httpRequest(originalRequest);
            } else {
                localStorage.removeItem("Authorization");
            }
        } else if(error.response.status === 401) {
            alert("로그인 후 이용해주세요.")
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
)

export default httpRequest;