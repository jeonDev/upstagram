import axios from "axios";
import {tokenReIssueRequest, TokenReIssueRequest} from "../api/LoginApi";

const serverIp = 'http://localhost:8090';

const httpRequest = axios.create({
    baseURL: serverIp,
    withCredentials: true,
    responseType: 'json'
})

httpRequest.interceptors.request.use(
    function(config) {

        const token = localStorage.getItem("Authorization");
        if(token != null && token != "")
            config.headers.Authorization = "Bearer " + token;

        return config;
    },
    function(error){
        return Promise.reject(error);
    }
);


httpRequest.interceptors.response.use(
    function(response) {
        console.log('response');
        console.log(response);
        return response;
    },
    function(error) {

        const originalRequest = error.config;
        // Access Token 만료 시, 토큰 재발급
        if(error.response.status === 401 && !originalRequest._retry) {
            tokenReIssueRequest()
                .then((response) => {
                    if(response.code == "200") {
                        const token = response.data.accessToken;
                        localStorage.setItem("Authorization", token);
                        originalRequest._retry = true;

                        return axios(originalRequest);
                    }
                })
                .catch((error) => {
                    console.log(error);
                });
        }
        return Promise.reject(error);
    }
)

export default httpRequest;