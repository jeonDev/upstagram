//import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Link, Route, Routes } from 'react-router-dom';
import OAuth2CallbackGoogle from './oauth/OAuth2CallbackGoogle';
import Join from "./component/Login/Join";
import Main from "./component/Main/Main";
import Dm from "./component/Dm/Dm";
import DmList from "./component/Dm/DmList";
import Header from './template/Header';
import { selectUserInfo } from './api/LoginApi';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { setUserInfo } from './config/action';

function App() {
  
  const dispatch = useDispatch();

  const loginUserInfo = async() => {
    const token = localStorage.getItem("Authorization");
    if(!token) return;
  
    const result = await selectUserInfo();
    dispatch(setUserInfo(result.data));
  }
  
  useEffect(() => {
    loginUserInfo();
  }, []);

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

              {/* DM */}
              <Route path='/dm' element={<Dm/>}/>
              <Route path='/dm/list' element={<DmList/>}/>

              {/* Not Found */}
              <Route component={() => <Link to="/" />} />
          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
