//import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import GoogleOauthLogin from './oauth/GoogleOauthLogin';
import Login from './views/Login/Login';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path='/login' element={<Login/>}/>
          <Route path='/oauth/google' element={<GoogleOauthLogin/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
