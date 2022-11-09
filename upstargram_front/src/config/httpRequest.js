import axios from "axios";

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
        return Promise.reject(error);
    }
)

export default httpRequest;