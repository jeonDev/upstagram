import httpRequest from "../config/httpRequest";

const OAuth2CallbackGoogle = () => {
    const params = new URLSearchParams(window.location.hash.substring(1));

    const accessToken = params.get("access_token");

    console.log("Callback 호출!");
    console.log("access Token : " + accessToken);

    const accessTokenServerGet = async() => {
        await httpRequest.post('http://localhost:8090/oauth/google',{
            data: accessToken
        })
        .then(response => {
            console.log('fetch 1');
            console.log(response);
        })
        .then(response => {
            console.log('fetch 2');
            console.log(response);
        });
    }
    
    accessTokenServerGet();

    return (
        <div>
            access Token : {accessToken}
        </div>
    );
}
export default OAuth2CallbackGoogle;


/*


1. 프론트엔드에서 OAuth 로그인을 요청한다.                          O
2. 구글 서버에서 인가코드를 발행한다 (Access Token)                 O
3. 받은 인가 코드를 백엔드에 보내준다.                              
4. 인가코드를 이용하여 구글 서버에 사용자의 정보를 요청한다.
5. 올바른 인가코드를 받은 구글 서버는 해당 사용자의 정보를 제공한다.

*/