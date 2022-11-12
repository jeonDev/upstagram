//import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Link, Route, Routes } from 'react-router-dom';
import OAuth2CallbackGoogle from './oauth/OAuth2CallbackGoogle';
import Join from "./component/Login/Join";
import Main from "./component/Main/Main";
import Dm from "./component/Dm/Dm";
import DmList from "./component/Dm/DmList";
import Header from './template/Header';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Header/>
        <Routes>
            {/* Main */}
            <Route exact={true} path="/" element={<Main/>}/>
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
      </BrowserRouter>
    </div>
  );
}

export default App;
