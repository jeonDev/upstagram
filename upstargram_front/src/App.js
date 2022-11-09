//import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './component/Login/Login';
import OAuth2CallbackGoogle from './oauth/OAuth2CallbackGoogle';
import Join from "./component/Login/Join";
import Main from "./component/Main/Main";
import Dm from "./component/Dm/Dm";
import DmList from "./component/Dm/DmList";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>

            {/* Main */}
            <Route path='/main' element={<Main/>}/>

            {/* Login */}
            <Route path='/login' element={<Login/>}/>
            <Route path='/join' element={<Join/>}/>
            <Route path='/login/callback/' element={<OAuth2CallbackGoogle/>}/>

            {/* DM */}
            <Route path='/dm' element={<Dm/>}/>
            <Route path='/dm/list' element={<DmList/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
