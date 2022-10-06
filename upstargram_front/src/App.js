//import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './views/Login/Login';
import OAuth2CallbackGoogle from './oauth/OAuth2CallbackGoogle';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path='/login' element={<Login/>}/>
          <Route path='/login/callback/' element={<OAuth2CallbackGoogle/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
