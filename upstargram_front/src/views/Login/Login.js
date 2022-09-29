
const GoogleOauthLogin = () => {
    
    const googleLogin = () => {
        window.location.href = "https://accounts.google.com/o/oauth2/auth?client_id="
            + "826917971572-k2repj07adcidd5d4cdgm3cic7f396je.apps.googleusercontent.com"
            + "&redirect_uri=" + "http://localhost:8098/oauth/google"
            + "&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email&approval_prompt=force&access_type=offline"; 
    }

    return (
        <div>
            <input type="button" id="loginBtn" onClick={() => googleLogin()} value="login"/>
        </div>
    );
}
export default GoogleOauthLogin;