import axios from "axios";

const serverIp = 'http://localhost:8090';

const httpRequest = axios.create({
    baseURL: serverIp,
    withCredentials: true,
    responseType: 'json'
})

httpRequest.interceptors.request.use(
    function(config) {
        console.log('response');
        console.log(config);
    },
    function(err){
        console.log('에러');
        console.log(err);
    }
);


httpRequest.interceptors.response.use(
    function(config) {
        console.log('response');
        console.log(config);
    },
    function(err) {
        console.log('에러');
        console.log(err);
    }
)

export default httpRequest;
/*


let ADM_WAS_URL = `${process.env.REACT_APP_ADM_WAS_IP}`;

const client = axios.create({
  baseURL: ADM_WAS_URL,
  withCredentials: true,
  responseType: 'json'
});

client.interceptors.request.use(
  function(config) {

    // 주요 검색어 세션스토리지에 저장.
    try {
      console.log("config.url>>> " + config.url);
      const params = JSON.parse(JSON.stringify(config.params));
      console.log("params>>> " + JSON.stringify(config.params));
      // 1. 요청 파라미터(우선 URL param만)에서 주요 검색조건 확인.
      // 1-1. 휴대폰번호
      var hpNo = params.hpNo;
      // 1-2. 카드번호
      var cardNo = params.cardNo;
      // 1-3. 고객번호
      var userId = params.userId;
      
      // 1-4. 조회구분/값
      var searchGubun = params.searchGubun;
      var searchVal = params.searchVal;
      var searchCode = params.searchCode;
      var searchValue = params.searchValue;

      // 2. 검색조건 유효하면 세션에 set.
      // 2-1. 휴대폰번호
      if(hpNo != null) stringUtils.setSchParamHpNo(hpNo);
      // 2-2. 카드번호
      if(cardNo != null) stringUtils.setSchParamCardNo(cardNo);
      // 2-3. 고객번호
      if(userId != null) stringUtils.setSchParamUserId(userId);
      
      // 2-4. 조회구분/값
      if(searchGubun != null) {
        stringUtils.setSchParamGubun(searchGubun);
        if(searchVal != null) stringUtils.setSchParamSearchVal(searchGubun, searchVal);
      }
      if(searchCode != null) {
        stringUtils.setSchParamGubun(searchCode);
        if(searchValue != null) stringUtils.setSchParamSearchVal(searchCode, searchValue);
      }
      
      

    } catch(e) {
      console.log(e);
    }

    // 요청 성공 직전 호출됩니다.
    // axios 설정값을 넣습니다. (사용자 정의 설정도 추가 가능)
    config.headers.Authorization =
      'Bearer ' + window.sessionStorage.getItem('token');
    return config;
  },
  function(error) {
    // 요청 에러 직전 호출됩니다.
    return Promise.reject(error);
  }
);

client.interceptors.response.use(
  function(response) {

    const code = response.data.code;
    console.log("AXIOS INTERCEPTORS CODE :: " + code);
    if (response.data.code == "90000.01") {
      window.sessionStorage.removeItem("token");
      window.location.replace("/login");
    } else if (code.startsWith("91000")) {
      alert(response.data.message);
      window.location.replace("/main");
    }

    return response;
  },
  function(error) {

    alert(error.toString());
    return Promise.reject(error);
  }
);

export default client;


*/