//import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Link, Route, Routes } from 'react-router-dom';
import OAuth2CallbackGoogle from './oauth/OAuth2CallbackGoogle';
import Join from "./component/Login/Join";
import Main from "./component/Main/Main";
import Dm from "./component/Dm/Dm";
import Header from './template/Header';
import { selectUserInfo } from './api/LoginApi';
import { useDispatch } from 'react-redux';
import { setUserInfo } from './config/action';
import Mypage from './component/Login/Mypage';
import Search from "./component/Search/Search";
import FeedKeep from './component/Feed/Keep/FeedKeep';

function App() {
  
  const dispatch = useDispatch();

  const loginUserInfo = async() => {
    const token = localStorage.getItem("Authorization");
    if(!token) return;
  
    const result = await selectUserInfo();
    dispatch(setUserInfo(result.data));
  }
  
  loginUserInfo();

  return (
    <div className="App">
      <BrowserRouter>
        <Header/>
        <div className='main-layout'>
          <Routes>
              {/* Main */}
              <Route exact path="/" element={<Main/>}/>
              <Route path='/main' element={<Main/>}/>

              {/* Login */}
              <Route path='/join' element={<Join/>}/>
              <Route path='/login/callback/' element={<OAuth2CallbackGoogle/>}/>
              <Route path='/mypage' element={<Mypage/>}/>

              {/* DM */}
              <Route path='/dm' element={<Dm/>}/>

              {/* Search */}
              <Route path='/search' element={<Search/>}/>

              {/* Keep */}
              <Route path='/feed/keep' element={<FeedKeep/>}/>

              {/* Not Found */}
              <Route component={() => <Link to="/" />} />
          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
